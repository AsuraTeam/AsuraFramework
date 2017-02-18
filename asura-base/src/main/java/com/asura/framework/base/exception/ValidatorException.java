/**
 * @FileName: ValidatorException.java
 * @Package: com.asura.framework.base.exception
 * @author liusq23
 * @created 11/8/2016 9:44 AM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.base.exception;

/**
 * <p>
 *     参数校验异常：在异常报警体系可以忽略
 * </p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liusq23
 * @since 1.0
 * @version 1.0
 */
public class ValidatorException extends BusinessException {

    private static final long serialVersionUID = 1L;

    /**
     * 默认code为 10000002
     */
    private static int VALIDATOR_CODE = 10000002;

    /**
     * 构造器
     *
     * @param code    异常错误编码
     * @param message 异常详细信息
     * @param cause   异常原因
     */
    public ValidatorException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     * @param cause   异常原因
     */
    public ValidatorException(String message, Throwable cause) {
        super(VALIDATOR_CODE, message, cause);
    }

    /**
     * 构造器
     * @param code    异常错误编码
     * @param message 异常详细信息
     */
    public ValidatorException(int code, String message) {
        super(code, message);
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     */
    public ValidatorException(String message) {
        super(VALIDATOR_CODE, message);
    }


}
