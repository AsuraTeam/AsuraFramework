/**
 * @FileName: RedisServiceProxy.java
 * @Package com.asura.framework.cache.redisThree
 * 
 * @author zhangshaobin
 * @created 2014年12月9日 下午4:24:56
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.cache.redisThree;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * RedisService服务代理
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author YRJ
 * @since 1.0
 * @version 1.0
 */
public class RedisServiceProxy implements InvocationHandler {

	private RedisService target;

	public RedisService bind(final RedisService target) {
		this.target = target;
		return (RedisService) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass()
				.getInterfaces(), this);
	}

	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		Object result = null;
		//System.out.println("start");
		try {
			result = method.invoke(target, args);
		} finally {
		}
		//System.out.println("end");
		return result;
	}
}
