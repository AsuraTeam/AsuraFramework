/**
 * @FileName: CacheDel.java
 * @Package com.asura.framework.cache.redisOne
 * 
 * @author zhangshaobin
 * @created 2014年11月30日 上午3:55:55
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.cache.redisOne;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>清除hash缓存</p>
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
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheDel {

	String key();

	String fieldKey() default "";

	boolean selfControl() default false;

}
