/**
 * @FileName: CacheAspect.java
 * @Package com.asura.framework.cache.redisOne
 * 
 * @author zhangshaobin
 * @created 2014年11月30日 上午12:33:07
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.conf.subscribe;

import com.asura.framework.base.util.Check;
import com.netflix.config.DynamicPropertyFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * <p>切面</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jiangn18
 * @since 1.0
 * @version 1.0
 */
@Component
@Aspect
public class AsuraAspect {
	final static Logger logger = LoggerFactory.getLogger(AsuraAspect.class);

	@Around("@annotation(com.asura.framework.conf.subscribe.AsuraSubField)")
	public Object findValue(ProceedingJoinPoint pjp) throws Throwable {
		Method method = getMethod(pjp); // 获取被拦截方法对象
		AsuraSubField asuraSubField = method.getAnnotation(AsuraSubField.class);
		String key = asuraSubField.appName() + "." + asuraSubField.type() + "." + asuraSubField.code();
		String str= DynamicPropertyFactory.getInstance().getStringProperty(key, null).get();
		if(Check.NuNStrStrict(str)){
			str=ConfigSubscriber.getInstance().getConfigValue(key);
			if(!Check.NuNStrStrict(str)){
				ConfigSubscriber.getInstance().registConfig(key, asuraSubField.defaultValue());
			}
		}
		if(Check.NuNStrStrict(str)){
			str=asuraSubField.defaultValue();
		}
		return str;

	}
	public Method getMethod(ProceedingJoinPoint pjp) {
		//获取参数的类型
		Object[] args = pjp.getArgs();
		Class[] argTypes = new Class[pjp.getArgs().length];
		for (int i = 0; i < args.length; i++) {
			argTypes[i] = args[i].getClass();
		}

		Method method = null;

		try {
			method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argTypes);
		} catch (NoSuchMethodException e) {
			logger.info("方法异常：",e);
		} catch (SecurityException e) {
			logger.info("方法异常：",e);
		}
		return method;

	}
}
