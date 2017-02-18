/**
 * @FileName: RedisCacheClient.java
 * @Package com.asura.framework.cache.redisOne
 * 
 * @author zhangshaobin
 * @created 2014年11月27日 上午10:25:37
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.cache.redisOne;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 实现类
 * </p>
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
@Service("redisOperations")
@Deprecated
public class RedisCacheClient implements RedisOperations, ApplicationContextAware {

	private final static Logger logger = LoggerFactory.getLogger(RedisCacheClient.class);

	@Value("#{${redis.pool.maxIdle}}")
	int MAX_ACTIVE;

	@Value("#{${redis.pool.minIdle}}")
	int MAX_IDLE;
	
	@Value("#{${redis.pool.maxwait}}")
	int MAX_WAIT;
	
	@Value("#{${redis.pool.maxtotal}}")
	int MAX_TOTAL;

	@Value("#{${redis.timeout}}")
	int DEFAULT_TIMEOUT;
	
	@Value("#{'${redis.servers}'}")
	String servers;

	@Value("#{'${redis.app}'}")
	String app;

	private ShardedJedisPool pool;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		init();
	}

	public void init() {

		try {
			String[] hosts = servers.trim().split("\\|");

			List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();

			for (String host : hosts) {
				String[] ss = host.split(":");
				//升级 redis  构造变化
				JedisShardInfo shard = new JedisShardInfo(ss[0], Integer.parseInt(ss[1]), DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, 1);
				shards.add(shard);
			}

			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(MAX_ACTIVE);
			config.setMinIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setMaxTotal(MAX_TOTAL);

			pool = new ShardedJedisPool(config, shards, Hashing.MURMUR_HASH);
		} catch (NumberFormatException e) {
			System.out.println("redis客户端初始化连接异常!!!!!!!!!  链接参数:" + servers
					+ " " + DEFAULT_TIMEOUT + " " + app);
			logger.error("redis:{},exception:{}.", servers + " "
					+ DEFAULT_TIMEOUT + " " + app, e.getMessage());
		}
	}

	/**
	 * 获取字符串
	 */
	@Override
	public String get(String key) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = pool.getResource();
			key = getKeyAll(key);
			result = redis.get(key);
			return result;
		} catch (RuntimeException e) { 
	        if(redis != null ) {
				redis.close();
			}
	        logger.error("redis get(String key):", e);
	        return result;
		} finally{ 
            if(redis != null ) {
				redis.close();
			}
		}
	}

	/**
	 * 从 Redis 2.6.12 版本开始， SET 命令的行为可以通过一系列参数来修改：
	 * EX second ：设置键的过期时间为 second 秒。 SET key value EX second 效果等同于 SETEX key second value 。
	 * PX millisecond ：设置键的过期时间为 millisecond 毫秒。 SET key value PX millisecond 效果等同于 PSETEX key millisecond value 。
	 * NX ：只在键不存在时，才对键进行设置操作。 SET key value NX 效果等同于 SETNX key value 。
	 * XX ：只在键已经存在时，才对键进行设置操作。
	 *
	 * @param key
	 * @param value
	 * @param milliseconds
	 * @return
	 */
	public boolean setnx(String key, String value, long milliseconds) {
		ShardedJedis redis = null;
		try {
			redis = pool.getResource();
			key = getKeyAll(key);
			//nx|xx ex|px  seconds|milliseconds
			String result = redis.set(key, value, "nx", "px", milliseconds);
			return "OK".equalsIgnoreCase(result);
		} catch (RuntimeException e) {
			logger.error("redis setex(String key, int seconds, String value):", e);
			return false;
		} finally {
			redis.close();
		}
	}

	/**
	 * 存入字符串, 并设置失效时间
	 */
	@Override
	public void setex(String key, int seconds, String value) {
		ShardedJedis redis = null;
		try {
			redis = pool.getResource();
			key = getKeyAll(key);
			redis.setex(key, seconds, value);
		} catch (RuntimeException e) {
			logger.error("redis setex(String key, int seconds, String value):", e);
		} finally{
			redis.close();
		}
		
	}

	/**
	 * 如果没有值，存入字符串
	 *
	 * @param key
	 * @param value
	 * @return
	 * 		成功或者失败
	 */
	public boolean setnx(String key, String value) {
		ShardedJedis redis = null;
		try {
			redis = pool.getResource();
			key = getKeyAll(key);
			Long result = redis.setnx(key, value);
			return result == 1;
		} catch (RuntimeException e) {
			if(redis != null ) {
				pool.returnBrokenResource(redis);
			}
			logger.error("redis setnx(String key, String value):", e);
			return false;
		} finally{
			if(redis != null ) {
				pool.returnResource(redis);
			}
		}
	}

	/**
	 * 把对象放入Hash中
	 */
	@Override
	public void hset(String key, String field, Object obj) {
		ShardedJedis redis = null;
		try {
			redis = pool.getResource();
			key = getKeyAll(key);
			if (obj instanceof String) {
				redis.hset(key, field, (String) obj);
			} else {
				redis.hset(key, field, JsonEntityTransform.Object2Json(obj));
			}
		} catch (RuntimeException e) { 
	        if(redis != null ) {
		        pool.returnBrokenResource(redis);
		    }
	        logger.error("redis hset(String key, String field, Object obj):", e);
		} finally{ 
	         if(redis != null ) {
	            pool.returnResource(redis);
	         }
		}
		
	}

	/**
	 * 从Hash中获取对象,转换成制定类型
	 */
	@Override
	public <T> List<T> hgetValueOfList(String key, String field, Class<T> clazz) {
		ShardedJedis redis = null;
		List<T> entitys = null;
		try {
			redis = pool.getResource();
			key = getKeyAll(key);
			String value = redis.hget(key, field);
			if (value == null) {
				return null;
			}
			entitys = JsonEntityTransform.json2ObjectList(value, clazz);
			return entitys;
		} catch (RuntimeException e) { 
	        if(redis != null ) {
		        pool.returnBrokenResource(redis);
		    }
	        logger.error("redis hgetValueOfList(String key, String field, Class<T> clazz):", e);
	        return entitys;
		} finally{ 
	         if(redis != null ) {
	            pool.returnResource(redis);
	         }
		}
		
	}

	/**
	 * 从Hash中获取对象,转换成制定类型
	 */
	@Override
	public <T extends BaseEntity> T hgetValueOfEntity(String key, String field,
			Class<T> clazz) {
		ShardedJedis redis = null;
		T entity = null;
		try {
			redis = pool.getResource();
			key = getKeyAll(key);
			String value = redis.hget(key, field);
			if (value == null) {
				return null;
			}
			entity = JsonEntityTransform.json2Entity(value, clazz);
			return entity;
		} catch (RuntimeException e) { 
	        if(redis != null ) {
		        pool.returnBrokenResource(redis);
		    }
	        logger.error("redis hgetValueOfEntity(String key, String field,Class<T> clazz):", e);
	        return entity;
		} finally{ 
	         if(redis != null ) {
	            pool.returnResource(redis);
	         }
		}
		 
	}

	/**
	 * 从Hash中获取对象,转换成制定类型
	 */
	@Override
	public <T> T hgetValueOfObject(String key, String field, Class<T> clazz) {
		ShardedJedis redis = null;
		T t = null;
		try {
			redis = pool.getResource();
			key = getKeyAll(key);
			String value = redis.hget(key, field);
			if (value == null) {
				return null;
			}
		    t = JsonEntityTransform.json2Object(value, clazz);
			return t;
		} catch (RuntimeException e) { 
	        if(redis != null ) {
		        pool.returnBrokenResource(redis);
		    }
	        logger.error("redis hgetValueOfObject(String key, String field, Class<T> clazz):", e);
	        return t;
		} finally{ 
	         if(redis != null ) {
	            pool.returnResource(redis);
	         }
		}
	}

	@Override
	public String hget(String key, String field) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = pool.getResource();
			key = getKeyAll(key);
			result = redis.hget(key, field);
			return result;
		} catch (RuntimeException e) { 
	        if(redis != null ) {
		        pool.returnBrokenResource(redis);
		    }
	        logger.error("redis hget(String key, String field):", e);
	        return result;
		} finally{ 
	         if(redis != null ) {
	            pool.returnResource(redis);
	         }
		}
		
	}

	/**
	 * 通过hash key获取所有对象
	 * @param key
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T> Map<String, T> hgetAll(String key, Class<T> clazz) {
		ShardedJedis redis = null;
		Map<String, T> map = new HashMap<>();
		try {
			redis = pool.getResource();
			key = getKeyAll(key);
			Map<String, String> result = redis.hgetAll(key);
			if (!Check.NuNMap(result)) {
				for (String mapKey : result.keySet()) {
					map.put(mapKey, JsonEntityTransform.json2Object(result.get(mapKey), clazz));
				}
			}
			return map;
		} catch (RuntimeException e) {
			if(redis != null ) {
				pool.returnBrokenResource(redis);
			}
			logger.error("redis hegeAll(String key):", e);
			return map;
		} finally{
			if(redis != null ) {
				pool.returnResource(redis);
			}
		}
	}

	@Override
	public boolean hexists(String key, String field) {
		ShardedJedis redis = null;
		boolean isExists = false;
		try {
			redis = pool.getResource();
			key = getKeyAll(key);
			isExists = redis.hexists(key, field);
			return isExists;
		} catch (RuntimeException e) { 
	        if(redis != null ) {
		        pool.returnBrokenResource(redis);
		    }
	        logger.error("redis hexists(String key, String field):", e);
	        return isExists;
		} finally{ 
	         if(redis != null ) {
	            pool.returnResource(redis);
	         }
		}
	}

	@Override
	public boolean exists(String key) {
		ShardedJedis redis = null;
		boolean isExists = false;
		try {
			redis = pool.getResource();
			key = getKeyAll(key);
			isExists = redis.exists(key);
			return isExists;
		} catch (RuntimeException e) { 
	        if(redis != null ) {
		        pool.returnBrokenResource(redis);
		    }
	        logger.error("redis exists(String key):", e);
	        return isExists;
		} finally{ 
	         if(redis != null ) {
	            pool.returnResource(redis);
	         }
		}
	}

	/**
	 * 从Hash中删除对象
	 */
	@Override
	public void hdel(String key, String... fields) {
		ShardedJedis redis = null;
		try {
			redis = pool.getResource();
			key = getKeyAll(key);
			redis.hdel(key, fields);
		} catch (RuntimeException e) {
	        if(redis != null ) {
		        pool.returnBrokenResource(redis);
		    }
	        logger.error("redis hdel(String key, String... fields):", e);
		} finally{ 
	         if(redis != null ) {
	            pool.returnResource(redis);
	         }
		}
	}

	/**
	 * 从string中删除对象
	 */
	@Override
	public void del(String key) {
		ShardedJedis redis = null;
		try {
			redis = pool.getResource();
			key = getKeyAll(key);
			redis.del(key);
		} catch (RuntimeException e) { 
	        if(redis != null ) {
		        pool.returnBrokenResource(redis);
		    }
	        logger.error("redis hdel(String key, String... fields):", e);
		} finally{ 
	         if(redis != null ) {
	            pool.returnResource(redis);
	         }
		}
		
	}

	/**
	 * 给对应key设置存活时间
	 */
	@Override
	public long expire(String key, int seconds) {
		ShardedJedis redis = null;
		long r = 0L;
		try {
			redis = pool.getResource();
			key = getKeyAll(key);
			r = redis.expire(key, seconds);
			return r;
		} catch (RuntimeException e) { 
	        if(redis != null ) {
		        pool.returnBrokenResource(redis);
		    }
	        logger.error("redis expire(String key, int seconds):", e);
	        return r;
		} finally{ 
	         if(redis != null ) {
	            pool.returnResource(redis);
	         }
		}
		
	}

	private String getKeyAll(String key) {
		return app + "_" + key;
	}

}
