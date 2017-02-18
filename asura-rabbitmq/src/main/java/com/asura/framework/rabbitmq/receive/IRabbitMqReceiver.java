/**
 * @FileName: IRabbitMqReceiver.java
 * @Package: com.asura.framework.rabbitmq.receive
 * @author sence
 * @created 3/9/2016 6:01 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbitmq.receive;

import com.asura.framework.rabbitmq.exception.AsuraRabbitMqException;

/**
 * <p>RabbitMQ 消费者接口</p>
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
public interface IRabbitMqReceiver {

    /**
     * 此方法主要实现接受消息
     * 一般实现接口的类需要聚合监听器，接受消息后通知监听器，处理具体的消费消息逻辑
     */
    void receiveMessage() throws Exception;

    /**
     * 销毁释放资源
     * @throws Exception
     */
    void destroyResource() throws AsuraRabbitMqException;

}
