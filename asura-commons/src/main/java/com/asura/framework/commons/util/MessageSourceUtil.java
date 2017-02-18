/*
 * Copyright (c) 2017 Asura
 */
package com.asura.framework.commons.util;

import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunzhenlei
 * @version 1.0
 * @date 2016/11/2 15:38
 * @since 1.0
 */
public class MessageSourceUtil {

    /**
     * 查找错误消息
     *
     * @param source
     * @param code
     *
     * @return
     */
    public static String getChinese(MessageSource source, String code) {
        return getChinese(source, code, new Object[]{});
    }

    /**
     * 查找错误消息
     *
     * @param source
     * @param code
     * @param params
     *
     * @return
     */
    public static String getChinese(MessageSource source, String code, Object[] params) {
        return getChinese(source, code, params, null);
    }

    /**
     * 查找错误消息
     *
     * @param source
     * @param code
     * @param params
     * @param defaultMsg
     *
     * @return
     */
    public static String getChinese(MessageSource source, String code, Object[] params, String defaultMsg) {
        if (Check.isNull(source)) {
            throw new IllegalArgumentException("message source is null");
        }
        if (Check.isNullOrEmpty(code)) {
            throw new IllegalArgumentException("code is empty");
        }
        //如果没有object 默认设置为空数组
        if (Check.isNull(params)) {
            params = new Object[]{};
        }
        if (Check.isNullOrEmpty(defaultMsg)) {
            return source.getMessage(code, params, Locale.SIMPLIFIED_CHINESE);
        } else {
            return source.getMessage(code, params, defaultMsg, Locale.SIMPLIFIED_CHINESE);
        }
    }

    /**
     * 查找错误消息
     *
     * @param source
     * @param code
     *
     * @return
     */
    public static int getIntMessage(MessageSource source, String code) {
        return getIntMessage(source, code, new Object[]{});
    }

    /**
     * 查找错误消息
     *
     * @param source
     * @param code
     * @param params
     *
     * @return
     */
    public static int getIntMessage(MessageSource source, String code, Object[] params) {

        return getIntMessage(source, code, params, null);
    }

    /**
     * 查找错误消息
     *
     * @param source
     * @param code
     * @param params
     * @param defaultMsg
     *
     * @return
     */
    public static int getIntMessage(MessageSource source, String code, Object[] params, String defaultMsg) {
        if (Check.isNull(source)) {
            throw new IllegalArgumentException("message source is null");
        }
        if (Check.isNullOrEmpty(code)) {
            throw new IllegalArgumentException("code is empty");
        }
        //如果没有object 默认设置为空数组
        if (Check.isNull(params)) {
            params = new Object[]{};
        }
        String message = null;

        if (Check.isNullOrEmpty(defaultMsg)) {
            message = source.getMessage(code, params, Locale.SIMPLIFIED_CHINESE);
        } else {
            message = source.getMessage(code, params, defaultMsg, Locale.SIMPLIFIED_CHINESE);
        }
        if (Check.isNullOrEmpty(message)) {
            throw new NumberFormatException("message is empty");
        } else {
            return Integer.valueOf(message);
        }
    }
}
