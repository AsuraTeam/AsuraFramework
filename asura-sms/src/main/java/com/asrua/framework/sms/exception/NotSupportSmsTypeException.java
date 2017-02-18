/**
 * @FileName: NotSupportSmsTypeException.java
 * @Package: com.asrua.framework.sms.exception
 * @author sence
 * @created 10/12/2015 9:23 AM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asrua.framework.sms.exception;

/**
 * <p>不支持的短信发送类型</p>
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
public class NotSupportSmsTypeException extends RuntimeException {

    /**
     *
     */
    public NotSupportSmsTypeException() {

    }

    /**
     * @param message
     */
    public NotSupportSmsTypeException(String message) {
        super(message);
    }

}
