/**
 * @FileName: AsuraRabbitMqException.java
 * @Package: com.asura.framework.rabbitmq.exception
 * @author sence
 * @created 3/12/2016 11:03 AM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbitmq.exception;

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
public class AsuraRabbitMqException extends RuntimeException {

    /**
     *
     */
    public AsuraRabbitMqException() {

    }

    /**
     * @param message
     */
    public AsuraRabbitMqException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public AsuraRabbitMqException(String message, Throwable cause) {
        super(message, cause);
    }
}
