/*
 * Copyright (c) 2017 Asura
 */
package com.asura.framework.commons.util;

import com.google.common.base.Strings;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

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
public class Check {

    /**
     * 工具类:私有化构造
     */
    private Check() {

    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return true:空字符串，false：非空字符串
     */
    public static boolean isNullOrEmpty(@Nullable String str) {
        return Strings.isNullOrEmpty(str);
    }

    /**
     * 判断字符串是否为空
     *
     * @param strs
     * @return true:空字符串，false：非空字符串
     */
    public static boolean isNullOrEmpty(@Nullable String... strs) {
        if(isNull(strs)){
            return true;
        }
        for(String str:strs){
            if(isNullOrEmpty(str)){
                return true;
            }
        }
        return false;
    }
    /**
     * 判断集合是否为空
     *
     * @param collection
     * @return true:空集合，false：非空集合
     */
    public static boolean isNullOrEmpty(@Nullable Collection collection) {
        return isNull(collection) || collection.isEmpty();
    }

    /**
     * 判断映射表是否为空
     *
     * @param map
     * @return true:空map false:非空map
     */
    public static boolean isNullOrEmpty(@Nullable Map map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * 判断是否非空对象
     *
     * @param obj
     * @return true:空对象，false：非空对象
     */
    public static boolean isNull(@Nullable Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }

    /**
     * 判断数组内是否有非空对象
     *
     * @param objs
     * @return true:空对象，false：非空对象
     */
    public static boolean isNullObjects(@Nullable Object... objs) {
        if (isNull(objs)) {
            return true;
        }
        for (Object obj : objs) {
            if (isNull(obj)) {
                return true;
            }
        }
        return false;
    }

}
