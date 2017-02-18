/**
 * @FileName: T_RedisSentinelClient.java
 * @Package: com.asura.framework.cache
 * @author liusq23
 * @created 2016/12/29 下午2:22
 * <p>
 * Copyright 2017 Asura
 */
package com.asura.framework.cache;

import com.asura.framework.cache.redisOne.RedisOperations;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

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
@org.springframework.test.context.ContextConfiguration(locations = {"classpath:applicationcontext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class T_RedisSentinelClient {

    @Resource(name = "redisSentinelCache")
    private RedisOperations redisOperations;

    @Before
    public void before() {
        assertNotNull(redisOperations);
    }

    /**
     * 测试 setex
     */
    @Test
    public void t_set_01() {
        redisOperations.setex("name", 5, "sence");
        String value = redisOperations.get("name");
        assertEquals(value, "sence");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNull(redisOperations.get("name"));
    }

    /**
     * 测试 setnx
     */
    @Test
    public void t_set_02() {
        redisOperations.setnx("name2", "haha", 3000);
        String value = redisOperations.get("name2");
        assertEquals(value, "haha");
        redisOperations.setnx("name2", "hehe", 3000);
        String value2 = redisOperations.get("name2");
        assertEquals(value2, "haha");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        redisOperations.setnx("name2", "hihi", 3000);
        String value3 = redisOperations.get("name2");
        assertEquals(value3, "hihi");
    }


    /**
     * 测试 hset
     */
    @Test
    public void t_hset_03() {
        redisOperations.hset("name", "20116234", "刘胜琦");
        String name = redisOperations.hget("name", "20116234");
        assertEquals(name, "刘胜琦");
    }


    /**
     * 测试 hset
     */
    @Test
    public void t_hset_04() {
        for(int i=0;i<1000;i++) {
            System.out.print(i);
            redisOperations.hset("name", "20116234", "" + i);
        }
    }

}
