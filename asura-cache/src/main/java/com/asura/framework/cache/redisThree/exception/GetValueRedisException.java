package com.asura.framework.cache.redisThree.exception;

/**
 * 
 * 从Redis中读取缓存数据异常.
 * 
 * @author YRJ
 *
 */
public class GetValueRedisException extends RedisException {

	private static final long serialVersionUID = 1L;

	public GetValueRedisException(final String message) {
		super(message);
	}

	public GetValueRedisException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
