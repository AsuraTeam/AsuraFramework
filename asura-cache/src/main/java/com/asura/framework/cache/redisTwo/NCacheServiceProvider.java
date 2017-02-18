package com.asura.framework.cache.redisTwo;

import java.util.Collection;
import java.util.Collections;

import redis.clients.jedis.ShardedJedis;

public class NCacheServiceProvider implements NCacheService {

	private ShardedJedis jedis;

	public NCacheServiceProvider() {
	}

	public void setJedis(final ShardedJedis jedis) {
		this.jedis = jedis;
	}

	@Override
	public void set(final String key, final String value) {
		jedis.set(key, value);
	}

	@Override
	public String get(final String key) {
		return jedis.get(key);
	}

	@Override
	public void lpush(final String key, final String... values) {
		jedis.lpush(key, values);
	}

	@Override
	public void rpush(final String key, final String... values) {
		jedis.rpush(key, values);
	}

	@Override
	public String lpop(final String key) {
		return jedis.lpop(key);
	}

	@Override
	public void delete(final String key) {
		delete(Collections.singletonList(key));
	}

	@Override
	public void delete(final Collection<String> keys) {
		for (final String key : keys) {
			jedis.del(key);
		}
	}
}
