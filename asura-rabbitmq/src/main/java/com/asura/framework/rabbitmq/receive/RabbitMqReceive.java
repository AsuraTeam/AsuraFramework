/**
 * @FileName: RabbitMqReceive.java
 * @Package com.asure.framework.rabbitmq.receive
 * @author zhangshaobin
 * @created 2016年3月1日 下午2:03:30
 * <p/>
 * Copyright 2011-2015 asura
 */
package com.asura.framework.rabbitmq.receive;

import com.asura.framework.rabbitmq.connection.RabbitConnectionFactory;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * <p>接受者</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class RabbitMqReceive {

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 100, 3000L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    private RabbitConnectionFactory rcf;

    private IRabbitMqMessageLisenter rabbitMqMessageLitener;

    private String queueName;

    public RabbitMqReceive(RabbitConnectionFactory rcf, IRabbitMqMessageLisenter rabbitMqMessageLitener, String queueName) {
        this.rcf = rcf;
        this.rabbitMqMessageLitener = rabbitMqMessageLitener;
        this.queueName = queueName;
    }

    /**
     *
     * @param queueName
     * @throws Exception
     * @throws TimeoutException
     */
    public void consumerQueueMessage() throws TimeoutException, Exception {
        threadPoolExecutor.execute(new ReceiveThread(rcf, rabbitMqMessageLitener, queueName));
        System.out.println("999999999999999999999999");
    }
}

class ReceiveThread implements Runnable {

    private RabbitConnectionFactory rcf;

    private IRabbitMqMessageLisenter rabbitMqMessageLitener;

    private String queueName;

    public ReceiveThread(RabbitConnectionFactory rcf, IRabbitMqMessageLisenter rabbitMqMessageLitener, String queueName) {
        this.rcf = rcf;
        this.rabbitMqMessageLitener = rabbitMqMessageLitener;
        this.queueName = queueName;
    }


    @Override
    public void run() {
        System.out.println(queueName + "=======+++++++===========");
        try {
            Connection connection = rcf.getConnection();
            Channel channel = rcf.getChannel(connection);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.basicQos(1);
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, false, consumer);
            while (true) {
                Transaction tran = Cat.newTransaction("rabbitmq-receive", queueName);
                try {
                    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                    rabbitMqMessageLitener.processMessage(delivery);
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    Cat.logMetricForCount(queueName); // 统计请求次数, 可以查看对应队列中放入了多少信息
                    tran.setStatus(Transaction.SUCCESS);
                } catch (Exception e) {
                    Cat.logError("接受消息异常.....", e);
                    tran.setStatus(e);
                } finally {
                    tran.complete();
                }
            }
        } catch (ShutdownSignalException e) {
            e.printStackTrace();
        } catch (ConsumerCancelledException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
