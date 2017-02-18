/**
 * @FileName: ExcutorRabbitMqTopicReceiver.java
 * @Package: com.asura.framework.rabbitmq.receive
 * @author sence
 * @created 3/9/2016 7:31 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbitmq.receive.topic;

import com.asura.framework.rabbitmq.PublishSubscribeType;
import com.asura.framework.rabbitmq.connection.RabbitConnectionFactory;
import com.asura.framework.rabbitmq.entity.BindingKey;
import com.asura.framework.rabbitmq.entity.ExchangeName;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <p>多线程消费</p>
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
public class ExcutorRabbitMqTopicReceiver extends AbstractRabbitMqTopicReceiver {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExcutorRabbitMqTopicReceiver.class);
    private static final int DEFAULT_POOL_SIZE = 1;
    /**
     *
     */
    private ExecutorService executorService;

    private int poolSize;

    public ExcutorRabbitMqTopicReceiver() {
        this.poolSize = DEFAULT_POOL_SIZE;
    }

    public ExcutorRabbitMqTopicReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners,
                                        QueueName queueName, BindingKey bindingKey, ExchangeName exchangeName) {
        this(rabbitConnectionFactory, rabbitMqMessageLiteners, queueName, bindingKey, exchangeName, PublishSubscribeType.DIRECT, 1);
    }

    public ExcutorRabbitMqTopicReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners,
                                        QueueName queueName, BindingKey bindingKey, ExchangeName exchangeName, int poolSize) {
        this(rabbitConnectionFactory, rabbitMqMessageLiteners, queueName, bindingKey, exchangeName, PublishSubscribeType.DIRECT, poolSize);
    }

    public ExcutorRabbitMqTopicReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners,
                                        BindingKey bindingKey, ExchangeName exchangeName) {
        this(rabbitConnectionFactory, rabbitMqMessageLiteners, null, bindingKey, exchangeName, PublishSubscribeType.DIRECT, 1);
    }

    public ExcutorRabbitMqTopicReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners,
                                        BindingKey bindingKey, ExchangeName exchangeName, int poolSize) {
        this(rabbitConnectionFactory, rabbitMqMessageLiteners, null, bindingKey, exchangeName, PublishSubscribeType.DIRECT, poolSize);
    }

    public ExcutorRabbitMqTopicReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners,
                                        BindingKey bindingKey, ExchangeName exchangeName, PublishSubscribeType publishSubscribeType, int poolSize) {
        this(rabbitConnectionFactory, rabbitMqMessageLiteners, null, bindingKey, exchangeName, publishSubscribeType, poolSize);
    }

    public ExcutorRabbitMqTopicReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners,
                                        QueueName queueName, BindingKey bindingKey, ExchangeName exchangeName, PublishSubscribeType publishSubscribeType, int poolSize) {
        super(rabbitConnectionFactory, rabbitMqMessageLiteners, queueName, bindingKey, exchangeName, publishSubscribeType);
        this.poolSize = poolSize;
    }

    @Override
    public void doConsumeTopicMessage(Connection connection, String environment) throws IOException, InterruptedException {
        executorService = Executors.newFixedThreadPool(poolSize);
        for (int size = 0; size < this.getPoolSize(); size++) {
            executorService.submit(new ConsumeWorker(getiReceiveFailover(), connection, environment, getExchangeName(), getQueueName(), getBindingKey(), getPublishSubscribeType().getName(), getRabbitMqMessageLiteners(), getErrorHandler()));
        }
    }

    @Override
    public void destroyResource() throws AsuraRabbitMqException {
        try {
            super.destroyResource();
            this.executorService.shutdown();
            this.executorService.awaitTermination(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LOGGER.warn("destroy error ", e);
            throw new AsuraRabbitMqException(e.getMessage());
        }
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    private class ConsumeWorker implements Runnable {

        private QueueName queueName;

        private Connection connection;

        private ExchangeName exchangeName;

        private BindingKey bindingKey;

        private String routingType;

        private String environment;

        private List<IRabbitMqMessageLisenter> lisenters;

        private IErrorHandler errorHandler;

        private IReceiveFailover iReceiveFailover;

        private ConsumeWorker(IReceiveFailover iReceiveFailover, Connection connection,
                              String environment, ExchangeName exchangeName, QueueName queueName,
                              BindingKey bindingKey, String routingType, List<IRabbitMqMessageLisenter> lisenters,
                              IErrorHandler errorHandler) {
            this.bindingKey = bindingKey;
            this.exchangeName = exchangeName;
            this.routingType = routingType;
            this.connection = connection;
            this.lisenters = lisenters;
            this.queueName = queueName;
            this.environment = environment;
            this.iReceiveFailover = iReceiveFailover;
            this.errorHandler = errorHandler;
        }

        @Override
        public void run() {
            Channel channel = null;
            try {
                channel = connection.createChannel();
                String _exchangeName = exchangeName.getNameByEnvironment(environment);
                channel.exchangeDeclare(_exchangeName, routingType, true);
                String qname;
                if (queueName == null) {
                    qname = channel.queueDeclare().getQueue();
                } else {
                    qname = queueName.getNameByEnvironment(environment);
                    channel.queueDeclare(qname, true, false, false, null);
                }
                channel.queueBind(qname, _exchangeName, bindingKey.getKey());
                QueueingConsumer consumer = new QueueingConsumer(channel);
                channel.basicConsume(qname, false, consumer);
                while (true) {
                    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                    Transaction trans = Cat.newTransaction("RabbitMQ Message", "CONSUME-TOPIC-" + _exchangeName);
                    String message = new String(delivery.getBody(), "UTF-8");
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info("CONSUMER TOPIC MESSAGE:[exchange:{},queue:{},bindingKey:{},message:{}]", _exchangeName, qname, bindingKey.getKey(), message);
                    }
                    Cat.logEvent("mq consumer exchange", _exchangeName, Event.SUCCESS,bindingKey.getKey()+":"+message);
                    Cat.logMetricForCount("CONSUME-TOPIC-" + _exchangeName);
                    try {
                        for (IRabbitMqMessageLisenter lisenter : lisenters) {
                            lisenter.processMessage(delivery);
                        }
                        trans.setStatus(Transaction.SUCCESS);
                    } catch (Exception e) {
                        Cat.logError("队列[" + _exchangeName + "," + qname + "," + bindingKey.getKey() + "]消费异常", e);
                        if (LOGGER.isErrorEnabled()) {
                            LOGGER.error("CONSUMER TOPIC MESSAGE:[exchange:{},queue:{},bindingKey:{},message:{}]", _exchangeName, qname, bindingKey.getKey(), message);
                        }
                        if (errorHandler != null) {
                            errorHandler.handlerTopicConsumeError(exchangeName.getName(), qname, bindingKey.getKey(), message, e);
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
