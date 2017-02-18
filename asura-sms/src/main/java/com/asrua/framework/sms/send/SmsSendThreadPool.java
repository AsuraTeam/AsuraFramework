/**
 * @FileName: SmsSendThreadPool.java
 * @Package: com.asrua.framework.sms.send
 * @author sence
 * @created 10/10/2015 9:22 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asrua.framework.sms.send;

import com.asrua.framework.sms.conf.AbstractSmsSenderConfig;
import com.asrua.framework.sms.log.ISmsLogger;
import com.asura.framework.base.entity.DataTransferObject;
import org.apache.http.client.methods.HttpPost;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <p></p>
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
public class SmsSendThreadPool {

    /**
     * 线程池size
     */
    private ISmsLogger logger;

    private ExecutorService threadPool;

    public SmsSendThreadPool(int poolSize, ISmsLogger logger) {
        threadPool = Executors.newFixedThreadPool(poolSize);
        this.logger = logger;
    }

    /**
     * 执行
     *
     * @param smsMessage
     * @param httpPost
     * @return
     */
    public Future<DataTransferObject> sumitTask(SmsMessage smsMessage, HttpPost httpPost, AbstractSmsSenderConfig config) {
        return threadPool.submit(new SmsSendWorker(smsMessage, httpPost, logger, config));
    }

}
