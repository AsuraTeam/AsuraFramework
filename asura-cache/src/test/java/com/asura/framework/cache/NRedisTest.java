package com.asura.framework.cache;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asura.framework.cache.redisTwo.NCacheService;

public class NRedisTest {
	private ApplicationContext context;

	private NCacheService service;

	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		service = context.getBean(NCacheService.class);
	}

	@Test
	public void testSet() {
		final long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			service.set("String-KEY-" + i, "Value-V-" + i);
		}
		final long end = System.currentTimeMillis();
		System.out.println("set times: " + (end - start));
	}

	@Test
	public void testGet() {
		final long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			final String value = service.get("String-KEY-" + i);
			System.out.println(value);
		}
		final long end = System.currentTimeMillis();
		System.out.println("get times: " + (end - start));
	}

	@Test
	public void testLpush() {
		final long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			service.lpush("List-Key-" + i, "Java" + i, "C++" + i, "PHP-" + i);
		}
		final long end = System.currentTimeMillis();
		System.out.println("lpush times: " + (end - start));
	}

	@Test
	public void testLpop() {
		final long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			final String value = service.lpop("List-Key-" + i);
			System.out.println(value);
		}
		final long end = System.currentTimeMillis();
		System.out.println("lpush times: " + (end - start));
	}
}
