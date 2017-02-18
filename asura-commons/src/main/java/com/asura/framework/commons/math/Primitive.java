/*
 * Copyright (c) 2017 Asura
 */
package com.asura.framework.commons.math;

/**
 * 提供Primitive类型的格式化操作
 *
 * @author YRJ
 */
public final class Primitive {

    private Primitive() {
        throw new AssertionError("Uninstantiable class");
    }

    /**
     * 将字符串转化为int类型数值, 若转化失败, 则返回为默认的转化值
     *
     * @param s
     * @param defaultValue
     *
     * @return
     */
    public static final int parseInt(final String s, final int defaultValue) {
        return parseInt(s, defaultValue, 10);
    }

    /**
     * 将字符串转化为int类型数值, 若转化失败, 则返回为默认的转化值
     *
     * @param s
     * @param defaultValue
     * @param radix
     *         进制
     *
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
}
