/**
 * @FileName: RedisServiceFactory.java
 * @Package com.asura.framework.cache.redisThree
 * 
 * @author zhangshaobin
 * @created 2014年12月9日 下午4:04:41
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.cache.redisThree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import com.asura.framework.cache.redisThree.hostinfo.HostInfo;
import com.asura.framework.cache.redisThree.hostinfo.HostInfoFactory;
import com.asura.framework.cache.redisThree.hostinfo.JedisPoolConfigFactory;

/**
 * Redis服务工厂.
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
public class RedisServiceFactory implements InitializingBean, FactoryBean<RedisService> {

	private String hosts = "127.0.0.1";

	/**
	 * @param 设置Redis缓存主机名、端口、及超时时间. 配置例子如下：
	 * 
	 * <p>192.168.12.19将采用默认的端口6379及超时时间3000ms</p>
	 * <p>192.168.12.19:6379将采用默认超时时间3000ms</p>
	 * <p>192.168.12.19:6379:2000,192.168.12.20,192.168.12.21:8081</p>
	 * 
	 */
	public void setHosts(final String hosts) {
		this.hosts = hosts;
	}

	private RedisService redisService;

	/**
	 * @param 配置RedisService. 提供用户自定义实现. 若用户未指定, 则采用默认服务实现RedisServiceImpl.
	 */
	public void setRedisService(final RedisService redisService) {
		this.redisService = redisService;
	}

	@Override
	public RedisService getObject() throws Exception {
		return redisService;
	}

	@Override
	public Class<?> getObjectType() {
		return RedisService.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		final JedisPoolConfig config = JedisPoolConfigFactory.createJedisPoolConfig();

		final Set<JedisShardInfo> shardInfos = new HashSet<JedisShardInfo>();
		final HostInfo[] hostInfos = HostInfoFactory.split(hosts);
		for (final HostInfo hostInfo : hostInfos) {
			shardInfos.add(hostInfo.createJedisShardInfo());
		}
		if (redisService == null) {
			final ShardedJedisPool jedisPool = new ShardedJedisPool(config, new ArrayList<JedisShardInfo>(shardInfos));
			redisService = new RedisServiceImpl(jedisPool);
		}

		final RedisServiceProxy redisServiceProxy = new RedisServiceProxy();
		this.redisService = redisServiceProxy.bind(redisService);
	}
}
