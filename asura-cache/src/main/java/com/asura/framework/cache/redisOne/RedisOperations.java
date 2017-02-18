/**
 * @FileName: CacheClient.java
 * @Package com.asura.framework.cache.redisOne
 * 
 * @author zhangshaobin
 * @created 2014年11月27日 上午10:26:05
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.cache.redisOne;

import com.asura.framework.base.entity.BaseEntity;

import java.util.List;

/**
 * <p>实现缓存客户端的封装</p>
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
public interface RedisOperations {

	/**
	 * 获取字符串
	 */
	public String get(String key);

	/**
	 * 存入字符串, 并设置失效时间
	 */
	public void setex(String key, int seconds, String value);

	/**
	 * 字符串不存在，设置值，返回成功或失败
	 * @param key
	 * @param value
	 */
	public boolean setnx(String key, String value);

	/**
	 * 字符串不存在，设置值，并设置存活生命时长
	 * 底层实现的是 redis命令 set nx px milliseconds
	 * jedis set(key, value, "nx", "px", milliseconds);
	 * <p>
	 * 返回成功或失败
	 *
	 * @param key
	 * @param value
	 */
	public boolean setnx(String key, String value, long milliseconds);

	/**
	 * 把对象放入Hash中
	 */
	public void hset(String key, String field, Object obj);

	/**
	 * 从Hash中获取对象, 该对象是json字符串
	 */
	public String hget(String key, String field);

	/**
	 * 从Hash中获取对象,转换成制定类型
	 */
	public <T> List<T> hgetValueOfList(String key, String field, Class<T> clazz);

	/**
	 * 从Hash中获取对象,转换成制定类型
	 */
	public <T extends BaseEntity> T hgetValueOfEntity(String key, String field, Class<T> clazz);

	/**
	 * 从Hash中获取对象,转换成制定类型
	 */
	public <T> T hgetValueOfObject(String key, String field, Class<T> clazz);

	/**
	 * 判断是否存在该key
	 */
	public boolean hexists(String key, String field);

	/**
	 * 从Hash中删除对象
	 */
	public void hdel(String key, String... fields);
	
	/**
	 * 从string中删除对象
	 */
	public void del(String key);

	/**
	 * 判断是否存在该key
	 */
	public boolean exists(String key);

	/**
	 * 给对应key设置存活时间
	 */
	public long expire(String key, int seconds);

}
