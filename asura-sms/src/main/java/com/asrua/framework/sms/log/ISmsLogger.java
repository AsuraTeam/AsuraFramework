/**
 * @FileName: ISmsLogger.java
 * @Package: com.asrua.framework.sms.log
 * @author sence
 * @created 10/10/2015 1:50 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asrua.framework.sms.log;

/**
 * <p>
 * 短信日志类
 * 可以允许有不同的实现，如写入文本，写入数据库，默认写入文本
 * </p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
public interface ISmsLogger {


    /**
     * 保存短信进入日志
     *
     * @param logInfoBean 短信发送状态
     */
    void saveLog(LogInfoBean logInfoBean);

}
