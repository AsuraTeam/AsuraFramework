package com.asura.framework.cache.redisThree.exception;

/**
 * 
 * Redis请求参数有误. 参数为空.
 * @author YRJ
 *
 */
public class ParameterRedisException extends RedisException {

	private static final long serialVersionUID = 1L;

	public ParameterRedisException(final String message) {
		super(message);
	}

}
