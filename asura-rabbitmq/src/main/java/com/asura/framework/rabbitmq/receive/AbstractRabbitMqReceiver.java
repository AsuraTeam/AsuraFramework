/**
 * @FileName: AbstractRabbitMqReceiver.java
 * @Package: com.asura.framework.rabbitmq.receive
 * @author sence
 * @created 3/9/2016 5:22 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbitmq.receive;

import com.asura.framework.rabbitmq.connection.RabbitConnectionFactory;
import com.asura.framework.rabbitmq.exception.AsuraRabbitMqException;
import com.asura.framework.rabbitmq.receive.errorHandler.IErrorHandler;
import com.asura.framework.rabbitmq.receive.failover.IReceiveFailover;
import com.asura.framework.rabbitmq.receive.failover.SampleReceiveFailover;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public abstract class AbstractRabbitMqReceiver implements IRabbitMqReceiver {


    private Logger LOGGER = LoggerFactory.getLogger(AbstractRabbitMqReceiver.class);
    /**
     * MQ factory
     */
    private RabbitConnectionFactory rabbitConnectionFactory;
    /**
     * 链接
     */
    private Connection connection;

    /**
     * 消息实际的监听器
     */
    private List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners;

    /**
     * 消费异常处理
     */
    private IErrorHandler errorHandler;

    private IReceiveFailover iReceiveFailover;

    public AbstractRabbitMqReceiver() {
        iReceiveFailover = new SampleReceiveFailover(this);
    }

    public AbstractRabbitMqReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners) {
        if(rabbitConnectionFactory==null){
            this.rabbitConnectionFactory = new RabbitConnectionFactory();
            rabbitConnectionFactory.init();
        }else{
            this.rabbitConnectionFactory = rabbitConnectionFactory;
        }
        this.rabbitMqMessageLiteners = rabbitMqMessageLiteners;
        iReceiveFailover = new SampleReceiveFailover(this);
    }


    @Override
    public void receiveMessage() throws Exception {
        if (rabbitMqMessageLiteners == null || rabbitMqMessageLiteners.isEmpty()) {
            throw new AsuraRabbitMqException("rabbitMqMessageLiteners not init");
        }
        /**
         * http://stackoverflow.com/questions/18418936/rabbitmq-and-relationship-between-channel-and-connection
         * A Connection represents a real TCP connection to the message broker
         * where as a channel is a virtual connection inside it.
         * This way you can use as many (virtual) connections as you want inside your application without overloading the broker with TCP connections
         */
        connection = rabbitConnectionFactory.getConnection();
        doConsumeMessage(connection, rabbitConnectionFactory.getEnvironment());
    }

    @Override
    public void destroyResource() throws AsuraRabbitMqException {
        try {
            if(connection.isOpen()){
                connection.close();
            }
        }catch (IOException e){
            LOGGER.warn("destroy resource error ",e);
            throw new AsuraRabbitMqException(e.getMessage());
        }
    }
    /**
     * 使用单个Connection 来实现消费
     *
     * @param connection
     */
    protected abstract void doConsumeMessage(Connection connection, String environment) throws IOException, InterruptedException;

    public void setRabbitConnectionFactory(RabbitConnectionFactory rabbitConnectionFactory) {
        this.rabbitConnectionFactory = rabbitConnectionFactory;
    }


    public List<IRabbitMqMessageLisenter> getRabbitMqMessageLiteners() {
        return rabbitMqMessageLiteners;
    }

    public void setRabbitMqMessageLiteners(List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners) {
        this.rabbitMqMessageLiteners = rabbitMqMessageLiteners;
    }

    public IReceiveFailover getiReceiveFailover() {
        return iReceiveFailover;
    }

    public void setiReceiveFailover(IReceiveFailover iReceiveFailover) {
        this.iReceiveFailover = iReceiveFailover;
    }

    public IErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public void setErrorHandler(IErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }
}
