/**
 * @FileName: DefaultTextLogger.java
 * @Package: com.asrua.framework.sms.log
 * @author sence
 * @created 10/10/2015 3:18 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asrua.framework.sms.log;

import com.asura.framework.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>文本日志实现类</p>
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
public class DefaultTextLogger implements ISmsLogger {

    private Logger logger = LoggerFactory.getLogger(DefaultTextLogger.class);

    @Override
    public void saveLog(LogInfoBean logInfoBean) {
        LogUtil.info(logger, logInfoBean.toString());
    }
}
