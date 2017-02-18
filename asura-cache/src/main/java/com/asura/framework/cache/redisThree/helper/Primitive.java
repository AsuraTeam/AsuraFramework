/**
 * @FileName: Primitive.java
 * @Package com.asura.framework.cache.redisThree.helper
 * 
 * @author zhangshaobin
 * @created 2014年12月9日 下午4:20:04
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.cache.redisThree.helper;


/**
 * 提供Primitive类型的格式化操作
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
public final class Primitive {
	/**
	 * 将字符串转化为int类型数值, 若转化失败, 则返回为默认的转化值
	 * @param s
	 * @param defaultValue
	 * @return
	 */
	public static final int parseInt(final String s, final int defaultValue) {
		return parseInt(s, defaultValue, 10);
	}

	/**
	 * 将字符串转化为int类型数值, 若转化失败, 则返回为默认的转化值
	 * @param s
	 * @param defaultValue
	 * @param radix			进制
	 * @return
	 */
	public static final int parseInt(final String s, final int defaultValue, final int radix) {
		int result = defaultValue;

		if (s == null || s.trim().isEmpty()) {
			return result;
		}

		try {
			result = Integer.parseInt(s, 10);
		} catch (final NumberFormatException ignore) {
		}
		return result;
	}

	private Primitive() {
		throw new AssertionError("Uninstantiable class");
	}
}
