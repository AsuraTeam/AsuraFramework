package com.asura.framework.cache.redisThree.exception;

public class IncrementRedisException extends RedisException {

	private static final long serialVersionUID = 1L;

	public IncrementRedisException(final String message) {
		super(message);
	}

	public IncrementRedisException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
