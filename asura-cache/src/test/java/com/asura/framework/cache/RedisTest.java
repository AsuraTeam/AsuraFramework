package com.asura.framework.cache;

import com.asura.framework.cache.redisTwo.CacheService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;
import java.util.Map.Entry;

public class RedisTest {

	private ApplicationContext context;

	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("src/test/resources/applicationcontext.xml");
	}

	@Test
	public void testSet() {
		final CacheService service = context.getBean(CacheService.class);

		final long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++)
			service.set("T-1-" + i, "Value-1-T" + i);
		final long end = System.currentTimeMillis();
		System.out.println("set 1000 times: " + (end - start));
	}

	@Test
	public void testGet() {
		final CacheService service = context.getBean(CacheService.class);

		final long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			final String value = service.get("T-1-" + i);
			System.out.println("T-1-" + i + " == " + value);//get方法性能很快, 但是println性能很慢啊
		}
		final long end = System.currentTimeMillis();
		System.out.println("set 1000 times: " + (end - start));
	}

	@Test
	public void testPushList() {

		final long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			//			service.pushList(Position.LEFT, "L-1-" + i, "java-" + i, "C-" + i, "C++" + i, "Python-" + i, "PHP-" + i);
		}
		final long end = System.currentTimeMillis();
		System.out.println("set 1000 times: " + (end - start));
	}

	@Test
	public void testPopList() {
		final CacheService service = context.getBean(CacheService.class);

		final long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			final String value = service.popList("L-1-" + i);
			System.out.println(value);
		}
		final long end = System.currentTimeMillis();
		System.out.println("pop 1000 times: " + (end - start));
	}

	@Test
	public void testRangeList() {
		final CacheService service = context.getBean(CacheService.class);

		final long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			final List<String> values = service.rangeList("L-1-" + i);
			System.out.println("~~~~~~~~~~");
			for (final String value : values) {
				System.out.println(value);
			}
		}
		final long end = System.currentTimeMillis();
		System.out.println("range 1000 times: " + (end - start));
	}

	@Test
	public void setPushSet() {
		final CacheService service = context.getBean(CacheService.class);

		final long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			service.pushSet("S-1-" + i, "java-" + i, "C-" + i, "C++" + i, "Python-" + i, "PHP-" + i);
		}
		final long end = System.currentTimeMillis();
		System.out.println("set 1000 times: " + (end - start));
	}

	@Test
	public void testPopSet() {
		final CacheService service = context.getBean(CacheService.class);

		final long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			System.out.println("~~~~~~~~~~");
			final String value = service.popSet("S-1-" + i);
			System.out.println(value);
		}
		final long end = System.currentTimeMillis();
		System.out.println("pop set 1000 times: " + (end - start));
	}

	@Test
	public void testRangeSet() {
		final CacheService service = context.getBean(CacheService.class);

		final long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			final Set<String> values = service.rangeSet("S-1-" + i);
			System.out.println("~~~~~~~~~~");
			for (final String value : values) {
				System.out.println(value);
			}
		}
		final long end = System.currentTimeMillis();
		System.out.println("range set 1000 times: " + (end - start));
	}

	@Test
	public void testPutHash() {
		final CacheService service = context.getBean(CacheService.class);
		final long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			service.putHash("H-1", "Hash-Key-" + i, "Hash-Value-" + i);
		}
		final long end = System.currentTimeMillis();
		System.out.println("put hash 1000 times: " + (end - start));
	}

	@Test
	public void testPutAllHash() {
		final CacheService service = context.getBean(CacheService.class);
		final long start = System.currentTimeMillis();

		final Map<String, String> keyValue = new HashMap<String, String>();
		for (int i = 0; i < 1000; i++) {
			keyValue.put("Hash-Key-" + i, "Hash-Value-" + i);
		}
		service.putAllHash("Hash-KV", keyValue);
		final long end = System.currentTimeMillis();
		System.out.println("put hash 1000 times: " + (end - start));
	}

	@Test
	public void testEntries() {
		final CacheService service = context.getBean(CacheService.class);
		final long start = System.currentTimeMillis();
		final Map<Object, Object> hashMap = service.entries("Hash-KV");
		for (final Iterator<Entry<Object, Object>> iter = hashMap.entrySet().iterator(); iter.hasNext();) {
			final Entry<Object, Object> entry = iter.next();
			System.out.println(entry.getKey() + " ~~~~ " + entry.getValue());
		}
		final long end = System.currentTimeMillis();
		System.out.println("put hash 1000 times: " + (end - start));
	}
}
