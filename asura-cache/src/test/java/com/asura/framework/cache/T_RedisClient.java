/**
 * @FileName: T_RedisClient.java
 * @Package: com.asura.framework.cache
 * @author liusq23
 * @created 2016/11/19 下午4:47
 * <p>
 * Copyright 2017 Asura
 */
package com.asura.framework.cache;

import com.asura.framework.cache.redisOne.RedisCacheClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

public class T_RedisClient {
    private ApplicationContext context;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("applicationcontext.xml");
    }

    @Test
    public void testSet() throws InterruptedException {
        final RedisCacheClient service = context.getBean(RedisCacheClient.class);
        boolean s = service.setnx("as00001", "1202", 2000);
        Assert.assertTrue(s);
        boolean so = service.setnx("as00001", "1202");
        Assert.assertTrue(!so);
        String sk = service.get("as00001");
        Assert.assertEquals(sk, "1202");
        Thread.sleep(1000);
        boolean s1 = service.setnx("as00001", "1202", 2000);
        Assert.assertTrue(!s1);
        Thread.sleep(1000);
        boolean s2 = service.setnx("as00001", "1202", 2000);
        Assert.assertTrue(s2);
    }


}
