/*
 * Copyright (c) 2017 Asura
 */
package com.asura.framework.commons.date;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liusq23
 * @since 1.0
 * @version 1.0
 */
public class DatePattern {

    /**
     * 日期
     */
    public static final String DEFAULT_FORMAT_DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间 24小时制
     */
    public static final String DEFAULT_FORMAT_HOUR_PATTERN = "yyyy-MM-dd HH";
    /**
     *分钟
     */
    public static final String DEFAULT_FORMAT_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    /**
     *秒
     */
    public static final String DEFAULT_FORMAT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     *毫秒
     */
    public static final String DEFAULT_FORMAT_MILLISECOND_PATTERN = "yyyy-MM-dd HH:mm:ss.sss";

    /**
     * 私有化构造
     */
    private DatePattern() {

    }
}
