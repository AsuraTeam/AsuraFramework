package com.asura.framework.cache.redisTwo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存服务
 * @author YRJ
 *
 */
public interface CacheService {

	/**
	 * 入栈/出栈方向
	 * @author YRJ
	 *
	 */
	enum Position {
		/** 左 */
		LEFT,
		/** 右 */
		RIGHT;
	}

	/**
	 * 读取指定Key的值
	 * @param key	待查找的Key
	 * @return
	 */
	String get(String key);

	/**
	 * 设置指定Key的值
	 * @param key	指定的Key
	 * @param value	待设定的Value
	 */
	void set(String key, String value);

	/**
	 * List数据结构入栈
	 * @param position
	 * @param key
	 * @param values
	 */
	void pushList(Position position, String key, String... values);

	/**
	 * List数据结构出栈
	 * @param key
	 * @return
	 */
	String popList(String key);

	/**
	 * 读取List数据结构
	 * @param key
	 * @return
	 */
	List<String> rangeList(String key);

	/**
	 * Set数据结构入栈
	 * @param key
	 * @param values
	 */
	void pushSet(String key, String... values);

	/**
	 * Set数据结构出栈
	 * @param key
	 * @return
	 */
	String popSet(String key);

	/**
	 * 读取Set数据结构
	 * @param key
	 * @return
	 */
	Set<String> rangeSet(String key);

	/**
	 * Map数据结构入栈
	 * @param key
	 * @param hashKey
	 * @param hashValue
	 */
	void putHash(String key, Object hashKey, Object hashValue);

	/**
	 * Map数据结构入栈
	 * @param key
	 * @param keyValue
	 */
	void putAllHash(final String key, final Map<? extends Object, ? extends Object> keyValue);

	/**
	 * Map数据结构
	 * @param key
	 * @param hashKey
	 * @return
	 */
	Object getHash(String key, String hashKey);

	/**
	 * 遍历指定的Key.
	 * @param key
	 * @return
	 */
	Map<Object, Object> entries(String key);

	/**
	 * 删除Map数据结构中指定的HashKey.
	 * @param key
	 * @param hashKeys
	 */
	void delete(String key, Object... hashKeys);
}
