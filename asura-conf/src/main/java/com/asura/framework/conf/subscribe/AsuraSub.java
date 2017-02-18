/**
 * @FileName: AsuraSub.java
 * @Package com.asura.framework.conf.subscribe
 * 
 * @author zhangshaobin
 * @created 2014年12月8日 下午4:28:07
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.conf.subscribe;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>注册常量</p>
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
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AsuraSub {

}
