package com.asura.framework.cache.redisThree.exception;

/**
 * 
 * Redis操作异常基类
 * 
 * @author YRJ
 *
 */
public class RedisException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RedisException(final String message) {
		super(message);
	}

	public RedisException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
