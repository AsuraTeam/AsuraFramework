/**
 * @FileName: SystemException.java
 * @Package: com.asura.framework.base.exception
 * @author liusq23
 * @created 11/8/2016 9:32 AM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.base.exception;

import com.asura.framework.base.entity.DataTransferObject;

/**
 * <p>
 *     系统异常:在异常报警体系需要报警
 * </p>
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
public class SystemException extends BaseException {

    private static final long serialVersionUID = 1L;


    /**
     * 构造器
     *
     * @param message	异常详细信息
     * @param cause	异常原因
     */
    public SystemException(String message, Throwable cause) {
        super(DataTransferObject.SYS_ERROR, message, cause);
    }

    /**
     * 构造器
     *
     * @param message	异常详细信息
     */
    public SystemException(String message) {
        super(DataTransferObject.SYS_ERROR, message);
    }

    /**
     * 构造器
     *
     * @param cause	异常原因
     */
    public SystemException(Throwable cause) {
        super(DataTransferObject.SYS_ERROR, "", cause);
    }


}
