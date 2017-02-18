/**
 * @FileName: SampleRabbitMqReceiver.java
 * @Package: com.asura.framework.rabbitmq.receive
 * @author sence
 * @created 3/9/2016 6:23 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbitmq.receive.queue;

import com.asura.framework.rabbitmq.connection.RabbitConnectionFactory;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.exception.AsuraRabbitMqException;
import com.asura.framework.rabbitmq.receive.IRabbitMqMessageLisenter;
import com.asura.framework.rabbitmq.receive.errorHandler.IErrorHandler;
import com.asura.framework.rabbitmq.receive.failover.IReceiveFailover;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * <p>队列消息接受</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
public class RabbitMqQueueReceiver extends AbstractRabbitQueueReceiver {

    private final static Logger LOGGER = LoggerFactory.getLogger(RabbitMqQueueReceiver.class);

    /**
     *
     */
    public RabbitMqQueueReceiver() {

    }

    /**
     * @param rabbitConnectionFactory
     * @param rabbitMqMessageLiteners
     * @param queueName
     */
    public RabbitMqQueueReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners, QueueName queueName) {
        super(rabbitConnectionFactory, rabbitMqMessageLiteners, queueName);
    }


    /**
     * @param connection
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void doConsumeQueueMessage(Connection connection, String environment) throws IOException, InterruptedException {
        Thread worker = new Thread(new ConsumeWorker(this.getiReceiveFailover(), this.getQueueName(), connection, environment,
                this.getRabbitMqMessageLiteners(), getErrorHandler()));
        worker.start();
    }

    @Override
    public void destroyResource() throws AsuraRabbitMqException {
        super.destroyResource();
    }

    private class ConsumeWorker implements Runnable {

        private QueueName queueName;

        private Connection connection;

        private List<IRabbitMqMessageLisenter> lisenters;

        private String enviroment;

        private IReceiveFailover iReceiveFailover;

        private IErrorHandler errorHandler;

        private ConsumeWorker(IReceiveFailover iReceiveFailover, QueueName queueName,
                              Connection connection, String environment, List<IRabbitMqMessageLisenter> lisenters,
                              IErrorHandler errorHandler) {
            this.queueName = queueName;
            this.connection = connection;
            this.lisenters = lisenters;
            this.iReceiveFailover = iReceiveFailover;
            this.enviroment = environment;
            this.errorHandler = errorHandler;
        }

        @Override
        public void run() {
            Channel channel = null;

            try {
                if (queueName == null) {
                    throw new AsuraRabbitMqException("queueName not set");
                }
                channel = connection.createChannel();
                String _queueName = queueName.getNameByEnvironment(enviroment);
                channel.queueDeclare(_queueName, true, false, false, null);
                QueueingConsumer consumer = new QueueingConsumer(channel);
                channel.basicQos(1);
                channel.basicConsume(_queueName, false, consumer);
                while (true) {
                    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                    Transaction trans = Cat.newTransaction("RabbitMQ Message", "CONSUME-QUEUE-" + _queueName);
                    String message = new String(delivery.getBody(), "UTF-8");
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info("CONSUMER QUEUE MESSAGE:[queue:{},message:{}]", _queueName, message);
                    }
                    Cat.logEvent("mq consumer queue", _queueName, Event.SUCCESS,message);
                    Cat.logMetricForCount("CONSUME-QUEUE-" + _queueName);
                    try {
                        for (IRabbitMqMessageLisenter lisenter : lisenters) {
                            lisenter.processMessage(delivery);
                        }
                        trans.setStatus(Transaction.SUCCESS);
                    } catch (Exception e) {
                        Cat.logError("队列[" + _queueName + "]消费异常", e);
                        if (LOGGER.isErrorEnabled()) {
                            LOGGER.error("CONSUMER QUEUE MESSAGE ERROR:[queue:{},message:{}]", _queueName, message);
                        }
                        if (errorHandler != null) {
                            errorHandler.handlerQueueConsumeError(queueName.getName(), message, e);
                        }
                        trans.setStatus(e);
                    } finally {
                        trans.complete();
                    }
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            } catch (Exception e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("rabbmitmq consumer error:", e);
                }
                iReceiveFailover.doFailover();
            }

        }
    }

}
