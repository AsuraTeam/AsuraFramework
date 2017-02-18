/**
 * @FileName: JedisPoolConfigFactory.java
 * @Package com.asura.framework.cache.redisThree.hostinfo
 * 
 * @author zhangshaobin
 * @created 2014年12月9日 下午4:21:42
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.cache.redisThree.hostinfo;

import redis.clients.jedis.JedisPoolConfig;

/**
 * 提供JedisPoolConfig配置解析工作
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
public class JedisPoolConfigFactory {

	//private final static String MAX_WAIT = "redis.config.maxwait";

	private final static long MAX_WAIT_TIME = 2000;

	private final static int MAX_ACTIVE_TIME = 2000;

	public static JedisPoolConfig createJedisPoolConfig() {
		final JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxWaitMillis(maxWait());
		config.setMaxIdle(1);
		config.setMaxTotal(maxActive());
		return config;
	}

	private static int maxActive() {
		return MAX_ACTIVE_TIME;
	}

	private static long maxWait() {
		return MAX_WAIT_TIME;
	}

	public static void main(final String[] args) {
		JedisPoolConfigFactory.createJedisPoolConfig();
	}

	private JedisPoolConfigFactory() {
		throw new AssertionError("Uninstantiable class");
	}
}
