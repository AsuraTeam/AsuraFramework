/**
 * @FileName: RedisService.java
 * @Package com.asura.framework.cache.redisThree
 * 
 * @author zhangshaobin
 * @created 2014年12月9日 下午4:05:40
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.cache.redisThree;

import java.util.Map;

import com.asura.framework.cache.redisThree.callback.Callback;

/**
 * Redis服务接口
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
public interface RedisService {

	/** 成功标志 */
	String SUCCESS = "OK";

	/** 默认缓存失效时间.默认失效时间为60分钟 */
	int EXPIRE = 1 * 60 * 60;

	/**
	 * 
	 * 读取字符串类型缓存. 提供默认回调, 默认回调将直接返回缓存数据.
	 * 
	 * @return
	 */
	String get(String key);

	/**
	 * 
	 * 读取字符串类型缓存. 提供用户自定义回调
	 * 
	 * @param key		待查找的缓存Key
	 * @param callback	回调
	 * @return
	 */
	String get(String key, Callback<String> callback);

	/**
	 * 
	 * 保存缓存内容
	 * 
	 * @param key	缓存Key
	 * @param value	缓存内容
	 */
	boolean set(String key, String value);

	/**
	 * 
	 * 保存缓存内容. 提供用户自定义缓存失效时间.
	 * 
	 * @param key		缓存Key
	 * @param value		缓存内容
	 * @param expire	缓存失效时间
	 * @return
	 */
	boolean set(String key, String value, int expire);

	/**
	 * 
	 * 增加缓存值数据
	 * 
	 * @param key	缓存Key
	 * @return		返回新值
	 */
	long increment(String key);

	/**
	 * 
	 * 增加缓存值数据
	 * 
	 * @param key		缓存Key
	 * @param expire	缓存失效时间
	 * @return			返回新值
	 */
	long increment(String key, int expire);

	/**
	 * 
	 * 增加缓存值数据
	 * 
	 * @param key		缓存Key
	 * @param expire	缓存失效时间
	 * @param callback	回调
	 * @return			返回新值
	 */
	long increment(String key, int expire, Callback<Long> callback);

	/**
	 * 
	 * 保存Map数据结构缓存信息. 默认的缓存失效时间.
	 * 
	 * @param key		缓存Key
	 * @param value		缓存Value
	 * @return
	 */
	boolean saveMap(String key, Map<String, String> value);

	/**
	 * 
	 * 保存Map数据结构缓存信息. 提供用户自定义缓存失效时间
	 * 
	 * @param key		缓存Key
	 * @param value		缓存Value
	 * @param expire	缓存失效时间
	 * @return
	 */
	boolean saveMap(String key, Map<String, String> value, int expire);

	/**
	 * 
	 * 读取Map数据结构中指定的字段值. 提供默认的回调
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	String hget(String key, String field);

	/**
	 * 
	 * 读取Map数据结构中指定的字段值. 提供用户自定义回调接口.
	 * 
	 * @param key
	 * @param field
	 * @param callback
	 * @return
	 */
	String hget(String key, String field, Callback<String> callback);

	/**
	 * 
	 * 设置Map数据结构中指定Hash字段的值.
	 *
	 * @author YRJ
	 * @created 2014年12月9日 下午5:19:31
	 *
	 * @param key
	 * @param field
	 * @param value
	 */
	boolean hset(String key, String field, String value);

	/**
	 * 
	 * 设置Map数据结构中指定Hash字段的值.并重新设置Map的失效时间.
	 *
	 * @author YRJ
	 * @created 2014年12月9日 下午5:26:32
	 *
	 * @param key
	 * @param field
	 * @param value
	 * @param expire
	 * @return
	 */
	boolean hset(String key, String field, String value, int expire);

	/**
	 * 
	 * 设置Map数据结构中多个Hash字段的值.并重新设置Map的失效时间
	 *
	 * @author YRJ
	 * @created 2014年12月9日 下午5:29:58
	 *
	 * @param key
	 * @param hash
	 * @return
	 */
	boolean hmset(final String key, final Map<String, String> hash);

	/**
	 * 
	 * 设置Map数据结构中多个Hash字段的值.并重新设置Map的失效时间
	 *
	 * @author YRJ
	 * @created 2014年12月9日 下午5:30:40
	 *
	 * @param key
	 * @param hash
	 * @param expire
	 * @return
	 */
	boolean hmset(final String key, final Map<String, String> hash, int expire);

	/**
	 * 
	 * 读取Map数据结构中所有的字段值, 并封装成用户指定的对象
	 *
	 * @author YRJ
	 * @created 2014年12月9日 下午8:49:15
	 *
	 * @param key
	 * @param clazz
	 * @return
	 */
	<T> T hgetAll(String key, Class<T> clazz);

	/**
	 * 
	 * 读取Map数据结构中所有的字段值, 并提供用户子定义回调接口.
	 * 
	 * @param <T>
	 * @param key
	 * @param callback
	 */
	<T> T hgetAll(String key, Callback<T> callback);

	/**
	 * 
	 * 将多个值存入List数据结构. List数据结构可存在重复数据.
	 *
	 * @author YRJ
	 * @created 2014年12月10日 上午9:21:48
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	void lpush(String key, String... value);
}
