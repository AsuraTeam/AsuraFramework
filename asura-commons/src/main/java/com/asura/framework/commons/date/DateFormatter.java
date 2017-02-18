/**
 * @FileName: DateFormatter.java
 * @Package: com.asura.framework.commons.date
 * @author liusq23
 * @created 11/1/2016 5:19 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.commons.date;

import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 日期格式化，基于joda-time实现，对外暴露统一为JDK Date，提供
 * 1、基于JDK日期Date格式化
 * 2、基于时间戳格式化
 * </p>
 * <p/>
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
public class DateFormatter {
    /**
     * 私有化构造
     */
    private DateFormatter() {
    }

    /**
     * 格式化日期
     * <p/>
     * 返回日期格式为：yyyy-MM-dd
     *
     * @param date
     * @return date
     */
    public static String formatDate(@NotNull Date date) {
        Objects.requireNonNull(date, "format date must not null");
        return format(date, DatePattern.DEFAULT_FORMAT_DATE_PATTERN);
    }

    /**
     * 格式化日期
     * <p/>
     * 返回日期格式为：yyyy-MM-dd
     *
     * @param milliseconds 毫秒
     * @return date
     */
    public static String formatDate(long milliseconds) {
        return format(milliseconds, DatePattern.DEFAULT_FORMAT_DATE_PATTERN);
    }

    /**
     * 格式化日期
     * <p/>
     * 返回日期格式为：yyyy-MM-dd HH:mm:ss
     * 24小时制
     *
     * @param date
     * @return
     */
    public static String formatDateTime(@NotNull Date date) {
        Objects.requireNonNull(date, "format date must not null");
        return format(date, DatePattern.DEFAULT_FORMAT_DATETIME_PATTERN);
    }

    /**
     * 格式化日期
     * <p/>
     * 返回日期格式为：yyyy-MM-dd HH:mm:ss
     * 24小时制
     *
     * @param milliseconds 毫秒
     * @return
     */
    public static String formatDateTime(long milliseconds) {
        return format(milliseconds, DatePattern.DEFAULT_FORMAT_DATETIME_PATTERN);
    }

    /**
     * 按照指定格式，格式化日期
     * <p/>
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(@NotNull Date date, @NotNull String pattern) {
        Objects.requireNonNull(date, "format date must not null");
        Objects.requireNonNull(pattern, "date pattern must not null");
        return format(date.getTime(), pattern);
    }

    /**
     * 格式化日期
     * <p/>
     * 返回日期格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param milliseconds 毫秒
     * @return
     */
    public static String format(long milliseconds, @NotNull String pattern) {
        Objects.requireNonNull(pattern, "date pattern must not null");
        DateTime dateTime = new DateTime(milliseconds);
        return dateTime.toString(pattern);
    }

}
