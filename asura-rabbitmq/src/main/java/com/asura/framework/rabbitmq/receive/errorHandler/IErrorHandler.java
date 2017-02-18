/**
 * @FileName: IErrorHandler.java
 * @Package: com.asura.framework.rabbitmq.receive.errorHandler
 * @author liusq23
 * @created 7/25/2016 9:34 AM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbitmq.receive.errorHandler;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liusq23
 * @version 1.0
 * @since 1.0
 */
public interface IErrorHandler {


    /**
     * 处理Queue消费异常，可捕获到异常时的队列名称，消息内容，以及具体的异常栈信息
     *
     * @param queueName
     * @param message
     * @param e
     */
    void handlerQueueConsumeError(String queueName, String message, Exception e);

    /**
     * 处理topic消费异常
     *
     * @param exchangeName
     * @param queueName
     * @param bindKey
     * @param message
     * @param e
     */
    void handlerTopicConsumeError(String exchangeName, String queueName, String bindKey, String message, Exception e);

}
