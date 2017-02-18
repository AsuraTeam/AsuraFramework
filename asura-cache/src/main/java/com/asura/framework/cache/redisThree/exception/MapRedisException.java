/**
 * @FileName: MapRedisException.java
 * @Package com.asura.framework.cache.redisThree.exception
 * 
 * @author zhangshaobin
 * @created 2014年12月9日 下午5:36:30
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.cache.redisThree.exception;

/**
 * Map数据结构操作异常
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
public class MapRedisException extends RedisException {

	private static final long serialVersionUID = 1L;

	public MapRedisException(final String message) {
		super(message);
	}

	public MapRedisException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
