/*
 * Copyright (c) 2017. Asura.
 */
package com.asura.framework.cache.redisOne;

import com.asura.framework.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>分布式锁工具</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunzhenlei
 * @version 1.0
 * @date 2015/9/7 10:42
 * @since 1.0
 */
public class RedisSentinelLockHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(RedisSentinelLockHandler.class);

    /**
     * 获取锁重试次数
     */
    private static final int WAIT_COUNT = 50;

    /**
     * 获取锁重试间隔，毫秒
     */
    private static final int RETRY_INTERVAL = 50;

    /**
     * 锁前缀
     */
    private static final String LOCK_KEY_PREFIX = "lock_key_";

    RedisOperations redisOperations;

    /**
     * 获取分布式锁
     *
     * @param lockKey
     * @return
     */
    public boolean getLock(String lockKey) {
        return getLock(lockKey, WAIT_COUNT * RETRY_INTERVAL);
    }

    /**
     * 获取分布式锁
     *
     * @param lockKey 锁key
     * @param timeout 超时时间，毫秒
     * @return
     */
    public boolean getLock(String lockKey, int timeout) {
        String key = LOCK_KEY_PREFIX + lockKey;
        try {
            for (int i = 0; i < timeout / RETRY_INTERVAL; i++) {
                if (redisOperations.setnx(key, String.valueOf(1), timeout)) {
                    LogUtil.debug(LOGGER, "get lock success : key is {}", key);
                    return true;
                } else {
                    LogUtil.debug(LOGGER, "get lock failure : key is {}, count is {}", key, i);
                    Thread.sleep(RETRY_INTERVAL);
                }
            }
        } catch (Exception e) {
            LogUtil.debug(LOGGER, "get lock throw an exception key is {}, exception : {}", key, e);
        }
        LogUtil.debug(LOGGER, "get lock failure : key is {}", key);
        return false;
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey
     */
    public void releaseLock(String lockKey) {
        String key = LOCK_KEY_PREFIX + lockKey;
        redisOperations.del(key);
        LogUtil.debug(LOGGER, "delete lock success : key is {}", key);
    }

    public RedisOperations getRedisOperations() {
        return redisOperations;
    }

    public void setRedisOperations(RedisOperations redisOperations) {
        this.redisOperations = redisOperations;
    }
}
