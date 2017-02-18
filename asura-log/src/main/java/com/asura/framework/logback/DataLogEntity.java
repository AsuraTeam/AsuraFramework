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

import java.io.Serializable;

import com.asura.framework.base.entity.BaseEntity;

/**
 *
 * <p>系统日志数据日志实体</p>
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
public class DataLogEntity extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5138676334265605421L;
	//类名
	private String className;
	//方法名
	private String methodName;
	//参数
	private Object[] params;

	public DataLogEntity() {

	}

	public DataLogEntity(String className, String methodName, Object[] params) {
		this.className = className;
		this.methodName = methodName;
		this.params = params;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

}
