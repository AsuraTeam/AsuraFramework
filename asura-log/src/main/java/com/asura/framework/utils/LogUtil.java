/**
 * @FileName: LogUtil.java
 * @Package: com.asura.framework.base.util
 * @author sence
 * @created 7/27/2015 9:18 AM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.utils;

import org.slf4j.Logger;

/**
 * <p>日志工具类</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @since 1.0
 * @version 1.0
 */
public class LogUtil {

    /**
     * 工具类 私有化其构造，防止通过new创建对象
     * @from sonar
     */
    private LogUtil(){

    }

    /**
     * trace level
     * @param logger
     * @param s
     * @param objs
     */
    public static void trace(Logger logger,String s,Object... objs){
        if(logger.isTraceEnabled()){
            logger.trace(s, objs);
        }
    }
    /**
     * Debug level
     * @param logger
     * @param s
     * @param objs
     */
    public static void debug(Logger logger,String s,Object... objs){
        if(logger.isDebugEnabled()){
            logger.debug(s,objs);
        }
    }

    /**
     * info level
     * @param logger
     * @param s
     * @param objs
     */
    public static void info(Logger logger,String s,Object... objs){
        if(logger.isInfoEnabled()){
            logger.info(s, objs);
        }
    }

    /**
     * warn level
     * @param logger
     * @param s
     * @param objs
     */
    public static void warn(Logger logger,String s,Object... objs){
        if(logger.isWarnEnabled()){
            logger.warn(s, objs);
        }
    }

    /**
     * error level
     * @param logger
     * @param s
     * @param objs
     */
    public static void error(Logger logger,String s,Object... objs){
        if(logger.isErrorEnabled()){
            logger.error(s,objs);
        }
    }
    
   
}
