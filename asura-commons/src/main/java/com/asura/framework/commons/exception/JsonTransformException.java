/*
 * Copyright (c) 2017 Asura
 */
package com.asura.framework.commons.exception;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunzhenlei
 * @version 1.0
 * @date 2016/11/2 17:34
 * @since 1.0
 */
public class JsonTransformException extends RuntimeException {

    private static final long serialVersionUID = -3010456487227762210L;

    /**
     * 构造器
     */
    public JsonTransformException() {
        super();
    }

    /**
     * 构造器
     *
     * @param message
     *         异常详细信息
     * @param cause
     *         异常原因
     */
    public JsonTransformException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造器
     *
     * @param message
     *         异常详细信息
     */
    public JsonTransformException(String message) {
        super(message);
    }

    /**
     * 构造器
     *
     * @param cause
     *         异常原因
     */
    public JsonTransformException(Throwable cause) {
        super(cause);
    }
}
