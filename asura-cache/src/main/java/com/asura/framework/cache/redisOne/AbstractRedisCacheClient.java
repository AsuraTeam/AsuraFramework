/**
 * @FileName: AbstractRedisCacheClient.java
 * @Package: com.asura.framework.cache.redisOne
 * @author liusq23
 * @created 2016/12/28 下午8:28
 * <p>
 * Copyright 2017 Asura
 */
package com.asura.framework.cache.redisOne;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.commons.json.Json;
import com.asura.framework.commons.util.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liusq23
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractRedisCacheClient<E extends JedisCommands, M extends Pool<E>> implements RedisOperations, InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(AbstractRedisCacheClient.class);
    //最大空闲连接数, 默认8个
    private int maxIdle;
    //最小空闲连接数, 默认8个
    private int minIdle;
    //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
    private int maxWaitMillis;
    //最大连接数, 默认8个
    private int maxTotal;
    //创建链接时的超时时间
    private int connectTimeout;
    //每次socket链接超时时间
    private int socketTimeout;
    //服务器列表 ip:host|ip:host
    private String servers;
    //APP名称
    private String app;


    private M pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = initPool();
    }

    /**
     *
     */
    public abstract M initPool() throws Exception;

    /**
     * 获取poolconfig
     *
     * @return
     */
    public JedisPoolConfig getPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        if (getMaxIdle() != 0) {
            config.setMaxIdle(getMaxIdle());
        }
        if (getMinIdle() != 0) {
            config.setMinIdle(getMinIdle());
        }
        if (getMaxWaitMillis() != 0) {
            config.setMaxWaitMillis(getMaxWaitMillis());
        }
        if (getMaxTotal() != 0) {
            config.setMaxTotal(getMaxTotal());
        }
        config.setTestOnBorrow(true);
        config.setTestWhileIdle(true);
        return config;
    }

    /**
     * 从池内获取链接
     *
     * @return
     */
    public E obtainJedis() {
        return pool.getResource();
    }

    /**
     * 释放链接
     *
     * @param e
     */
    public abstract void releaseJedis(E e);

    /**
     * 获取字符串
     */
    @Override
    public String get(String key) {
        E redis = null;
        String result = null;
        try {
            redis = obtainJedis();
            key = getKeyAll(key);
            result = redis.get(key);
            return result;
        } catch (RuntimeException e) {
            logger.error("redis get(String key):", e);
            return result;
        } finally {
            releaseJedis(redis);
        }
    }

    /**
     * 从 Redis 2.6.12 版本开始， SET 命令的行为可以通过一系列参数来修改：
     * EX second ：设置键的过期时间为 second 秒。 SET key value EX second 效果等同于 SETEX key second value 。
     * PX millisecond ：设置键的过期时间为 millisecond 毫秒。 SET key value PX millisecond 效果等同于 PSETEX key millisecond value 。
     * NX ：只在键不存在时，才对键进行设置操作。 SET key value NX 效果等同于 SETNX key value 。
     * XX ：只在键已经存在时，才对键进行设置操作。
     *
     * @param key
     * @param value
     * @param milliseconds
     * @return
     */
    public boolean setnx(String key, String value, long milliseconds) {
        E redis = null;
        try {
            redis = obtainJedis();
            key = getKeyAll(key);
            String result = redis.set(key, value, "nx", "px", milliseconds);
            return "OK".equalsIgnoreCase(result);
        } catch (RuntimeException e) {
            logger.error("redis setex(String key, int seconds, String value):", e);
            return false;
        } finally {
            releaseJedis(redis);
        }
    }

    /**
     * 存入字符串, 并设置失效时间
     */
    @Override
    public void setex(String key, int seconds, String value) {
        E redis = null;
        try {
            redis = obtainJedis();
            key = getKeyAll(key);
            redis.setex(key, seconds, value);
        } catch (RuntimeException e) {
            logger.error("redis setex(String key, int seconds, String value):", e);
        } finally {
            releaseJedis(redis);
        }

    }

    /**
     * 如果没有值，存入字符串
     *
     * @param key
     * @param value
     * @return 成功或者失败
     */
    public boolean setnx(String key, String value) {
        E redis = null;
        try {
            redis = obtainJedis();
            key = getKeyAll(key);
            Long result = redis.setnx(key, value);
            return result == 1;
        } catch (RuntimeException e) {
            logger.error("redis setnx(String key, String value):", e);
            return false;
        } finally {
            releaseJedis(redis);
        }
    }

    /**
     * 把对象放入Hash中
     */
    @Override
    public void hset(String key, String field, Object obj) {
        E redis = null;
        try {
            redis = obtainJedis();
            key = getKeyAll(key);
            if (obj instanceof String) {
                redis.hset(key, field, (String) obj);
            } else {
                redis.hset(key, field, Json.toJsonString(obj));
            }
        } catch (RuntimeException e) {
            logger.error("redis hset(String key, String field, Object obj):", e);
        } finally {
            releaseJedis(redis);
        }

    }

    /**
     * 从Hash中获取对象,转换成制定类型
     */
    @Override
    public <T> List<T> hgetValueOfList(String key, String field, Class<T> clazz) {
        String value = hget(key, field);
        if (Check.isNullOrEmpty(value)) {
            return null;
        }
        return Json.getPathArray(value, clazz);
    }

    /**
     * 从Hash中获取对象,转换成制定类型
     */
    @Override
    public <T extends BaseEntity> T hgetValueOfEntity(String key, String field, Class<T> clazz) {
        String value = hget(key, field);
        if (Check.isNullOrEmpty(value)) {
            return null;
        }
        return Json.getPathObject(value, clazz);
    }

    /**
     * 从Hash中获取对象,转换成制定类型
     */
    @Override
    public <T> T hgetValueOfObject(String key, String field, Class<T> clazz) {
        String value = hget(key, field);
        if (Check.isNullOrEmpty(value)) {
            return null;
        }
        return Json.getPathObject(value, clazz);
    }

    @Override
    public String hget(String key, String field) {
        E redis = null;
        String result = null;
        try {
            redis = obtainJedis();
            key = getKeyAll(key);
            result = redis.hget(key, field);
            return result;
        } catch (RuntimeException e) {
            logger.error("redis hget(String key, String field):", e);
            return result;
        } finally {
            releaseJedis(redis);
        }

    }

    /**
     * 通过hash key获取所有对象
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Map<String, T> hgetAll(String key, Class<T> clazz) {
        E redis = null;
        Map<String, T> map = new HashMap<>();
        try {
            redis = obtainJedis();
            key = getKeyAll(key);
            Map<String, String> result = redis.hgetAll(key);
            if (!Check.isNullOrEmpty(result)) {
                for (String mapKey : result.keySet()) {
                    map.put(mapKey, Json.getPathObject(result.get(mapKey), clazz));
                }
            }
            return map;
        } catch (RuntimeException e) {
            logger.error("redis hegeAll(String key):", e);
            return map;
        } finally {
            releaseJedis(redis);
        }
    }

    @Override
    public boolean hexists(String key, String field) {
        E redis = null;
        boolean isExists = false;
        try {
            redis = obtainJedis();
            key = getKeyAll(key);
            isExists = redis.hexists(key, field);
            return isExists;
        } catch (RuntimeException e) {
            logger.error("redis hexists(String key, String field):", e);
            return isExists;
        } finally {
            releaseJedis(redis);
        }
    }

    @Override
    public boolean exists(String key) {
        E redis = null;
        boolean isExists = false;
        try {
            redis = obtainJedis();
            key = getKeyAll(key);
            isExists = redis.exists(key);
            return isExists;
        } catch (RuntimeException e) {
            logger.error("redis exists(String key):", e);
            return isExists;
        } finally {
            releaseJedis(redis);
        }
    }

    /**
     * 从Hash中删除对象
     */
    @Override
    public void hdel(String key, String... fields) {
        E redis = null;
        try {
            redis = obtainJedis();
            key = getKeyAll(key);
            redis.hdel(key, fields);
        } catch (RuntimeException e) {
            logger.error("redis hdel(String key, String... fields):", e);
        } finally {
            releaseJedis(redis);
        }
    }

    /**
     * 从string中删除对象
     */
    @Override
    public void del(String key) {
        E redis = null;
        try {
            redis = obtainJedis();
            key = getKeyAll(key);
            redis.del(key);
        } catch (RuntimeException e) {
            logger.error("redis hdel(String key, String... fields):", e);
        } finally {
            releaseJedis(redis);
        }

    }

    /**
     * 给对应key设置存活时间
     */
    @Override
    public long expire(String key, int seconds) {
        E redis = null;
        long r = 0L;
        try {
            redis = obtainJedis();
            key = getKeyAll(key);
            r = redis.expire(key, seconds);
            return r;
        } catch (RuntimeException e) {
            logger.error("redis expire(String key, int seconds):", e);
            return r;
        } finally {
            releaseJedis(redis);
        }

    }

    private String getKeyAll(String key) {
        return app + "_" + key;
    }


    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(int maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
