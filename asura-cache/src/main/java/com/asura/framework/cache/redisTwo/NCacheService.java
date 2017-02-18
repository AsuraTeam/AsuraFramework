package com.asura.framework.cache.redisTwo;

import java.util.Collection;

public interface NCacheService {

	/**
	 * 设置缓存
	 * @param key
	 * @param value
	 */
	void set(String key, String value);

	/**
	 * 读取缓存
	 * @param key
	 * @return
	 */
	String get(String key);

	/**
	 * 设置List结构缓存. left push
	 * @param key
	 * @param values
	 */
	void lpush(final String key, final String... values);

	/**
	 * 设置List结构缓存. right push
	 * @param key
	 * @param values
	 */
	void rpush(final String key, final String... values);

	/**
	 * 移除List结构数据.
	 * @param key
	 */
	String lpop(final String key);

	/**
	 * 删除指定Key
	 * @param key
	 */
	void delete(final String key);

	/**
	 * 批量删除key
	 * @param keys
	 */
	void delete(final Collection<String> keys);
}
