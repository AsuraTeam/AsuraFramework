/**
 * @FileName: AbstractRabbitQueueReceiver.java
 * @Package: com.asura.framework.rabbitmq.receive
 * @author sence
 * @created 3/9/2016 8:52 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbitmq.receive.queue;

import com.asura.framework.rabbitmq.connection.RabbitConnectionFactory;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.exception.AsuraRabbitMqException;
import com.asura.framework.rabbitmq.receive.AbstractRabbitMqReceiver;
import com.asura.framework.rabbitmq.receive.IRabbitMqMessageLisenter;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.List;

/**
 * <p></p>
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
public abstract class AbstractRabbitQueueReceiver extends AbstractRabbitMqReceiver {


    private QueueName queueName;

    public AbstractRabbitQueueReceiver() {
    }

    public AbstractRabbitQueueReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners, QueueName queueName) {
        super(rabbitConnectionFactory, rabbitMqMessageLiteners);
        this.queueName = queueName;
    }

    @Override
    protected void doConsumeMessage(Connection connection, String environment) throws IOException, InterruptedException {
        if (queueName == null) {
            throw new AsuraRabbitMqException("queueName not set");
        }
        doConsumeQueueMessage(connection, environment);
    }

    protected abstract void doConsumeQueueMessage(Connection connection, String environment) throws IOException, InterruptedException;

    public QueueName getQueueName() {
        return queueName;
    }

    public void setQueueName(QueueName queueName) {
        this.queueName = queueName;
    }
}
