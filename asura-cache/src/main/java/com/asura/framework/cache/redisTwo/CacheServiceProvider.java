package com.asura.framework.cache.redisTwo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * 缓存服务提供者
 * @author YRJ
 *
 */
public class CacheServiceProvider implements CacheService {

	/** 默认的缓存Key. 若缓存Key的前缀未指定, 则使用此前缀. */
	private static final String DEFAULT_PREFIX = "default:";

	/** 缓存Key的前缀. */
	protected String prefix = DEFAULT_PREFIX;

	protected StringRedisTemplate redisTemplate;

	public CacheServiceProvider() {
	}

	public CacheServiceProvider(final StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 设置缓存Key的前缀. 建议Key采用冒号(:)分割.
	 * <p>例如: user:id:123, user:id:456</p>
	 * <p>例如: prod:count:123, prod:count:456</p>
	 * @param prefix
	 */
	public void setPrefix(final String prefix) {
		String _prefix = prefix;
		if (prefix.charAt(prefix.length() - 1) != ':') {
			_prefix += ":";
		}
		this.prefix = _prefix;
	}

	public void setRedisTemplate(final StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 根据业务需求, 组装缓存Key.
	 * @param key
	 * @return
	 */
	protected String getKey(final String key) {
		return prefix + key;
	}

	@Override
	public String get(final String key) {
		final ValueOperations<String, String> operation = redisTemplate.opsForValue();
		return operation.get(getKey(key));
	}

	@Override
	public void set(final String key, final String value) {
		final ValueOperations<String, String> operation = redisTemplate.opsForValue();
		operation.set(getKey(key), value);
	}

	@Override
	public void pushList(final Position position, final String key, final String... values) {
		final ListOperations<String, String> operation = redisTemplate.opsForList();
		if (position == Position.LEFT) {
			operation.leftPushAll(getKey(key), values);
		} else {
			operation.rightPushAll(getKey(key), values);
		}
	}

	@Override
	public String popList(final String key) {
		final ListOperations<String, String> operation = redisTemplate.opsForList();
		final String value = operation.leftPop(getKey(key));
		return value;
	}

	@Override
	public List<String> rangeList(final String key) {
		final ListOperations<String, String> operation = redisTemplate.opsForList();
		final List<String> value = operation.range(getKey(key), 0, operation.size(getKey(key)));
		return value;
	}

	@Override
	public String popSet(final String key) {
		final SetOperations<String, String> operation = redisTemplate.opsForSet();
		final String value = operation.pop(getKey(key));
		return value;
	}

	@Override
	public void pushSet(final String key, final String... values) {
		final SetOperations<String, String> operation = redisTemplate.opsForSet();
		operation.add(getKey(key), values);
	}

	@Override
	public Set<String> rangeSet(final String key) {
		final SetOperations<String, String> operation = redisTemplate.opsForSet();
		final Set<String> value = operation.members(getKey(key));
		return value;
	}

	@Override
	public void putHash(final String key, final Object hashKey, final Object hashValue) {
		final HashOperations<String, Object, Object> operation = redisTemplate.opsForHash();
		operation.put(getKey(key), hashKey, hashValue);
	}

	@Override
	public void putAllHash(final String key, final Map<? extends Object, ? extends Object> keyValue) {
		final HashOperations<String, Object, Object> operation = redisTemplate.opsForHash();
		operation.putAll(getKey(key), keyValue);
	}

	@Override
	public Object getHash(final String key, final String hashKey) {
		final HashOperations<String, Object, Object> operation = redisTemplate.opsForHash();
		final Object value = operation.get(getKey(key), hashKey);
		return value;
	}

	@Override
	public Map<Object, Object> entries(final String key) {
		final HashOperations<String, Object, Object> operation = redisTemplate.opsForHash();
		return operation.entries(getKey(key));
	}

	@Override
	public void delete(final String key, final Object... hashKeys) {
		final HashOperations<String, Object, Object> operation = redisTemplate.opsForHash();
		operation.delete(getKey(key), hashKeys);
	}

}
