/**
 * @FileName:
 * @Package: com.asura.services.log
 *
 * @author sence
 * @created 11/3/2014 6:42 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.asura.framework.logback;

/**
 *
 * <p>系统日志错误日志实体</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @since 1.0
 * @version 1.0
 */
public class ErrorLogEntity extends DataLogEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7175924822251701372L;
	//抛出异常参数
	private String throwMessage;

	public ErrorLogEntity(DataLogEntity dle) {
		super(dle.getClassName(), dle.getMethodName(), dle.getParams());
	}

	public String getThrowMessage() {
		return throwMessage;
	}

	public void setThrowMessage(String throwMessage) {
		this.throwMessage = throwMessage;
	}

}
