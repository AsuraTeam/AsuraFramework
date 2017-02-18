/**
 * @FileName: RedisShardedCacheClient.java
 * @Package: com.asura.framework.cache.redisOne
 * @author liusq23
 * @created 2016/12/29 上午11:08
 * <p>
 * Copyright 2017 Asura
 */
package com.asura.framework.cache.redisOne;

import com.asura.framework.commons.util.Check;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Pool;

import java.util.ArrayList;
import java.util.List;

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
public class RedisShardedCacheClient extends AbstractRedisCacheClient<ShardedJedis, Pool<ShardedJedis>> {

    /**
     * connect timeout
     */
    private int connectTimeout;
    /**
     * socket timeout
     */
    private int socketTimeout;


    public RedisShardedCacheClient() {
        this.connectTimeout = 2000;
        this.socketTimeout = 2000;
    }

    @Override
    public Pool<ShardedJedis> initPool() throws Exception {
        if (Check.isNullOrEmpty(getServers())) {
            throw new IllegalArgumentException("未指定redis服务器地址");
        }
        String[] hosts = getServers().trim().split("\\|");
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        for (String host : hosts) {
            String[] ss = host.split(":");
            //升级 redis  构造变化
            JedisShardInfo shard = new JedisShardInfo(ss[0], Integer.parseInt(ss[1]), connectTimeout, socketTimeout, 1);
            shards.add(shard);
        }
        return new ShardedJedisPool(getPoolConfig(), shards, Hashing.MURMUR_HASH);
    }

    @Override
    public void releaseJedis(ShardedJedis shardedJedis) {
        shardedJedis.close();
    }


    @Override
    public int getConnectTimeout() {
        return connectTimeout;
    }

    @Override
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    @Override
    public int getSocketTimeout() {
        return socketTimeout;
    }

    @Override
    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
}
