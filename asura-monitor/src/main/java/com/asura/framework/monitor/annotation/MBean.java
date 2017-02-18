/**
 * @FileName: MBean.java
 * @Package com.asura.framework.monitor.annotation
 * 
 * @author zhangshaobin
 * @created 2013-1-14 上午8:49:54
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.monitor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>MBean注解，自动识别并加载、注册MBean</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MBean {
	
	String name();
	
}
