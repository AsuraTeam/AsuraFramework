/**
 * @FileName: RedisServiceImpl.java
 * @Package com.asura.framework.cache.redisThree
 * 
 * @author zhangshaobin
 * @created 2014年12月9日 下午4:23:53
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.cache.redisThree;

import java.util.Arrays;
import java.util.Map;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.asura.framework.cache.redisThree.callback.Callback;
import com.asura.framework.cache.redisThree.callback.DefaultMapCallback;
import com.asura.framework.cache.redisThree.exception.GetValueRedisException;
import com.asura.framework.cache.redisThree.exception.IncrementRedisException;
import com.asura.framework.cache.redisThree.exception.MapRedisException;
import com.asura.framework.cache.redisThree.exception.ParameterRedisException;

/**
 * Redis服务默认实现
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
public final class RedisServiceImpl implements RedisService {

	private final ShardedJedisPool jedisPool;

	public RedisServiceImpl(final ShardedJedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	@Override
	public String get(final String key) {
		return get(key, Callback.STRINGCALLBACK);
	}

	@Override
	public String get(final String key, final Callback<String> callback) {
		checkParameters(key, callback);
		final ShardedJedis jedis = getJedis();
		String value = null;
		try {
			value = callback.callback(jedis.get(key));
			return value;
		} catch (final Exception e) {
			throw new GetValueRedisException("method: get, key: " + key + ", value: " + value, e);
		} finally {
			returnResource(jedis);
		}
	}

	@Override
	public boolean set(final String key, final String value) {
		return set(key, value, EXPIRE);
	}

	@Override
	public boolean set(final String key, final String value, final int expire) {
		checkParameters(key, value);
		final ShardedJedis jedis = getJedis();
		final String result = jedis.set(key, value);
		jedis.expire(key, expire);
		returnResource(jedis);
		return SUCCESS.equalsIgnoreCase(result);
	}

	@Override
	public long increment(final String key) {
		return increment(key, EXPIRE);
	}

	@Override
	public long increment(final String key, final int expire) {
		return increment(key, expire, Callback.LONGCALLBACK);
	}

	@Override
	public long increment(final String key, final int expire, final Callback<Long> callback) {
		checkParameters(key, callback);
		final ShardedJedis jedis = getJedis();

		long incr = jedis.incr(key);
		jedis.expire(key, expire);

		try {
			incr = callback.callback(incr);
		} catch (final Exception e) {
			throw new IncrementRedisException("method: increment, key: " + key + ", incr: " + incr, e);
		}

		returnResource(jedis);
		return incr;
	}

	@Override
	public boolean saveMap(final String key, final Map<String, String> value) {
		return saveMap(key, value, EXPIRE);
	}

	@Override
	public boolean saveMap(final String key, final Map<String, String> value, final int expire) {
		checkParameters(key, value);

		final ShardedJedis jedis = getJedis();
		final String result = jedis.hmset(key, value);
		jedis.expire(key, expire);

		returnResource(jedis);
		return SUCCESS.equalsIgnoreCase(result);
	}

	@Override
	public String hget(final String key, final String field) {
		return hget(key, field, Callback.STRINGCALLBACK);
	}

	@Override
	public String hget(final String key, final String field, final Callback<String> callback) {
		checkParameters(key, field, callback);

		final ShardedJedis jedis = getJedis();
		String value = null;
		try {
			value = callback.callback(jedis.hget(key, field));
			return value;
		} catch (final Exception e) {
			throw new GetValueRedisException("method: hget, key: " + key + ", value: " + value, e);
		} finally {
			returnResource(jedis);
		}
	}

	@Override
	public boolean hset(final String key, final String field, final String value) {
		return hset(key, field, value, EXPIRE);
	}

	@Override
	public boolean hset(final String key, final String field, final String value, final int expire) {
		checkParameters(key, field, value);

		final ShardedJedis jedis = getJedis();
		final long result = jedis.hset(key, field, value);

		jedis.expire(key, expire);

		returnResource(jedis);
		return result == 0;
	}

	@Override
	public boolean hmset(final String key, final Map<String, String> hash) {
		return hmset(key, hash, EXPIRE);
	}

	@Override
	public boolean hmset(final String key, final Map<String, String> hash, final int expire) {
		checkParameters(key, hash);

		final ShardedJedis jedis = getJedis();
		try {
			final String result = jedis.hmset(key, hash);
			jedis.expire(key, expire);
			return SUCCESS.equalsIgnoreCase(result);
		} catch (final JedisConnectionException e) {
			throw new MapRedisException("method: hmset, key:" + key, e);
		} finally {
			returnResource(jedis);
		}
	}

	@Override
	public <T> T hgetAll(final String key, final Class<T> clazz) {
		checkParameters(key, clazz);
		return hgetAll(key, new DefaultMapCallback<T>(clazz));
	}

	@Override
	public <T> T hgetAll(final String key, final Callback<T> callback) {
		checkParameters(key, callback);

		final ShardedJedis jedis = getJedis();
		final Map<String, String> result = jedis.hgetAll(key);
		try {
			return callback.callback(result);
		} catch (final Exception e) {
			throw new GetValueRedisException("method: hgetAll, key: " + key + ", result: " + result, e);
		} finally {
			returnResource(jedis);
		}
	}

	@Override
	public void lpush(final String key, final String... value) {
		checkParameters(key, value);

		final ShardedJedis jedis = getJedis();
		jedis.lpush(key, value);
		returnResource(jedis);
	}

	/**
	 * 检测参数
	 * @param parameters
	 */
	private void checkParameters(final Object... parameters) {
		if (parameters == null || parameters.length == 0) {
			throw new ParameterRedisException("parameter is null.");
		}
		for (final Object parameter : parameters) {
			if (parameter == null) {
				throw new ParameterRedisException(Arrays.toString(parameters) + " have null.");
			}
		}
	}

	private ShardedJedis getJedis() {
		return jedisPool.getResource();
	}

	private void returnResource(final ShardedJedis jedis) {
		jedisPool.returnResource(jedis);
	}
}
