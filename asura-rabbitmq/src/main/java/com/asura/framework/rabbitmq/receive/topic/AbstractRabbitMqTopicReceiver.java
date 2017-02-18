/**
 * @FileName: AbstractRabbitMqTopicReceiver.java
 * @Package: com.asura.framework.rabbitmq.receive
 * @author sence
 * @created 3/9/2016 8:08 PM
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
public abstract class AbstractRabbitMqTopicReceiver extends AbstractRabbitMqReceiver {

    /**
     * 队列名称
     */
    private QueueName queueName;
    /**
     * 绑定key
     */
    private BindingKey bindingKey;
    /**
     * exchange 名称
     */
    private ExchangeName exchangeName;
    /**
     * 发布类型
     */
    private PublishSubscribeType publishSubscribeType;
    /**
     *
     */
    private Connection connection;

    public AbstractRabbitMqTopicReceiver() {

    }

    public AbstractRabbitMqTopicReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners,
                                         QueueName queueName, BindingKey bindingKey, ExchangeName exchangeName, PublishSubscribeType publishSubscribeType) {
        super(rabbitConnectionFactory, rabbitMqMessageLiteners);
        this.queueName = queueName;
        this.bindingKey = bindingKey;
        this.exchangeName = exchangeName;
        this.publishSubscribeType = publishSubscribeType;
    }

    @Override
    public void doConsumeMessage(Connection connection, String environment) throws IOException, InterruptedException {
        if (this.getBindingKey() == null) {
            throw new AsuraRabbitMqException("bindingKey not set");
        }
        if (this.getExchangeName() == null) {
            throw new AsuraRabbitMqException("exchangeName not set");
        }
        if (this.getPublishSubscribeType() == null) {
            throw new AsuraRabbitMqException("publishSubscribeType not set");
        }
        this.connection = connection;
        doConsumeTopicMessage(connection, environment);
    }

    protected abstract void doConsumeTopicMessage(Connection connection, String environment) throws IOException, InterruptedException;

    public QueueName getQueueName() {
        return queueName;
    }

    public void setQueueName(QueueName queueName) {
        this.queueName = queueName;
    }

    public BindingKey getBindingKey() {
        return bindingKey;
    }

    public void setBindingKey(BindingKey bindingKey) {
        this.bindingKey = bindingKey;
    }

    public ExchangeName getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(ExchangeName exchangeName) {
        this.exchangeName = exchangeName;
    }

    public PublishSubscribeType getPublishSubscribeType() {
        return publishSubscribeType;
    }

    public void setPublishSubscribeType(PublishSubscribeType publishSubscribeType) {
        this.publishSubscribeType = publishSubscribeType;
    }
}
