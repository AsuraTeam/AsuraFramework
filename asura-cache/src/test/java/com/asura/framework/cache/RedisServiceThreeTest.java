/**
 * @FileName: RedisServiceThreeTest.java
 * @Package com.asura.framework.cache
 * 
 * @author zhangshaobin
 * @created 2014年12月9日 下午4:30:04
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.cache;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asura.framework.base.util.Check;
import com.asura.framework.cache.redisThree.RedisService;
import com.asura.framework.cache.redisThree.callback.CallbackAdapter;
import com.asura.framework.cache.redisThree.helper.Primitive;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author YRJ
 * @since 1.0
 * @version 1.0
 */
public class RedisServiceThreeTest {

	private ApplicationContext context;

	private RedisService redisService;

	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("provider.xml");
		redisService = context.getBean(RedisService.class);
	}

	@Test
	public void test() {
		System.out.println(redisService);
		Assert.assertTrue(true);
	}

	@Test
	public void testSet() {
		final int total = 10000;
		final long start = System.currentTimeMillis();
		for (int i = 0; i < total; i++) {
			redisService.set("string-key-" + i, "string-value-" + i);
		}
		final long end = System.currentTimeMillis();
		System.out.println("set [" + total + "], times: " + (end - start));
	}

	@Test
	public void testGet() {
		final int total = 10000;
		long start = System.currentTimeMillis();
		for (int i = 0; i < total; i++) {
			redisService.get("string-key-" + i);
		}
		long end = System.currentTimeMillis();
		System.out.println("1 get [" + total + "], times: " + (end - start));

		start = System.currentTimeMillis();
		for (int i = 0; i < total; i++) {
			final String value = redisService.get("string-key-" + i, new CallbackAdapter<String>() {

				@Override
				public String callback(final String t) throws Exception {
					if (Check.NuNStr(t)) {
						//query from db.
						return t;
					}
					return "OK:" + t;
				}

			});
			System.out.println(value);
		}
		end = System.currentTimeMillis();
		System.out.println("2 get [" + total + "], times: " + (end - start));
	}

	@Test
	public void testIncrement() {
		final int total = 10000;
		final long start = System.currentTimeMillis();
		for (int i = 0; i < total; i++) {
			redisService.increment("incr-key", 60, new CallbackAdapter<Long>() {

				@Override
				public Long callback(final Long t) throws Exception {
					System.out.println(t);
					return t;
				}

			});
		}
		final long end = System.currentTimeMillis();
		System.out.println("increment [" + total + "], times: " + (end - start));
	}

	@Test
	public void testSaveMap() {
		final int total = 10000;
		final long start = System.currentTimeMillis();
		for (int i = 0; i < total; i++) {
			final Map<String, String> value = new HashMap<String, String>();
			value.put("id", String.valueOf(i));
			value.put("name", "YinRenjie-" + i);
			value.put("realname", "殷仁杰-" + i);
			value.put("password", "1234567890-" + i);
			value.put("wife", "哈哈哈-" + i);
			value.put("child", "No Field-" + i);
			value.put("isDo", String.valueOf(i / 5 == 0));

			redisService.saveMap("user:" + i, value);
		}
		final long end = System.currentTimeMillis();
		System.out.println("savemap [" + total + "], times: " + (end - start));
	}

	@Test
	public void testHget() {
		final int total = 10000;
		final long start = System.currentTimeMillis();
		for (int i = 0; i < total; i++) {
			final String name = redisService.hget("map-key-" + i, "name");
			System.out.println(name);
		}
		final long end = System.currentTimeMillis();
		System.out.println("hget [" + total + "], times: " + (end - start));
	}

	@Test
	public void testHset() {
		final int total = 10000;
		final long start = System.currentTimeMillis();
		for (int i = 0; i < total; i++) {
			redisService.hset("map-key-" + i, "password", "ABCDEFGHIJK-" + i);
		}
		final long end = System.currentTimeMillis();
		System.out.println("hset [" + total + "], times: " + (end - start));
	}

	@Test
	public void testHmset() {
		final int total = 10000;
		final long start = System.currentTimeMillis();
		for (int i = 0; i < total; i++) {
			final Map<String, String> value = new HashMap<String, String>();
			value.put("wife", "哈哈哈-" + i);
			value.put("child", "Yin Zijing-" + i);
			redisService.hmset("map-key-" + i, value);
		}
		final long end = System.currentTimeMillis();
		System.out.println("hmset [" + total + "], times: " + (end - start));
	}

	@Test
	public void testHgetAllDefaultCallback() {
		final int total = 10000;
		final long start = System.currentTimeMillis();
		for (int i = 0; i < total; i++) {
			final SubUserEntity entity = redisService.hgetAll("user:" + i, SubUserEntity.class);
			System.out.println(entity);
		}
		final long end = System.currentTimeMillis();
		System.out.println("hgetAll [" + total + "], times: " + (end - start));
	}

	@Test
	public void testHgetAllUserCallback() {
		final int total = 10000;
		final long start = System.currentTimeMillis();
		for (int i = 0; i < total; i++) {
			final UserEntity entity = redisService.hgetAll("map-key-" + i, new CallbackAdapter<UserEntity>() {

				@Override
				public UserEntity callback(final Map<String, String> map) throws Exception {
					final UserEntity entity = new UserEntity();
					entity.setId(Primitive.parseInt(map.get("id"), -1));
					entity.setName(map.get("name"));
					entity.setRealname(map.get("realname"));
					entity.setPassword(map.get("password"));
					return entity;
				}

			});
			System.out.println(entity);
		}
		final long end = System.currentTimeMillis();
		System.out.println("hgetAll [" + total + "], times: " + (end - start));
	}
}
