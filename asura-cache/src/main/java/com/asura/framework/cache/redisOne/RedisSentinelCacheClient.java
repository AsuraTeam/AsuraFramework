/**
 * @FileName: RedisSentinelCacheClient.java
 * @Package: com.asura.framework.cache.redisOne
 * @author liusq23
 * @created 2016/12/28 下午8:17
 * <p>
 * Copyright 2017 Asura
 */
package com.asura.framework.cache.redisOne;

import com.asura.framework.commons.util.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 哨兵模式 redis
 * </p>
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
public class RedisSentinelCacheClient extends AbstractRedisCacheClient<Jedis, JedisSentinelPool> implements InitializingBean {

    private static Logger LOGGER = LoggerFactory.getLogger(RedisSentinelCacheClient.class);

    private String masterName;

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    @Override
    public JedisSentinelPool initPool() throws Exception {
        if (Check.isNullOrEmpty(getServers())) {
            throw new IllegalArgumentException("未指定redis服务器地址");
        }
        String[] hosts = getServers().split("\\|");
        Set<String> hostAndPorts = new HashSet<>();
        for (String host : hosts) {
            hostAndPorts.add(host);
        }
        JedisPoolConfig poolConfig = super.getPoolConfig();
        return new JedisSentinelPool(getMasterName(), hostAndPorts, poolConfig);
    }

    @Override
    public void releaseJedis(Jedis jedis) {
        jedis.close();
    }
}
