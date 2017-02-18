/**
 * @FileName: SmsSendWorker.java
 * @Package: com.asrua.framework.sms.send
 * @author sence
 * @created 10/10/2015 9:22 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asrua.framework.sms.send;

import com.asrua.framework.sms.conf.AbstractSmsSenderConfig;
import com.asrua.framework.sms.log.ISmsLogger;
import com.asrua.framework.sms.log.LogInfoBean;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Callable;

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
public class SmsSendWorker implements Callable<DataTransferObject> {

    private Logger LOGGER = LoggerFactory.getLogger(AbstractHttpSmsSender.class);

    private AbstractSmsSenderConfig config;

    private HttpPost httpPost;

    private SmsMessage smsMessage;

    private ISmsLogger logger;


    public SmsSendWorker(SmsMessage smsMessage, HttpPost httpPost, ISmsLogger logger, AbstractSmsSenderConfig config) {
        this.smsMessage = smsMessage;
        this.httpPost = httpPost;
        this.logger = logger;
        this.config = config;
    }

    @Override
    public DataTransferObject call() throws Exception {
        CloseableHttpClient httpClient = null;
        DataTransferObject dto = new DataTransferObject();
        CloseableHttpResponse response = null;
        LogInfoBean logInfoBean = new LogInfoBean();
        try {
            /**
             * HTTP connections are complex, stateful, thread-unsafe objects which need to be properly managed to function correctly.
             * HTTP connections can only be used by one execution thread at a time
             */
            httpClient = HttpClients.createDefault();
            logInfoBean.setSmsMessage(smsMessage);
            logInfoBean.setUrl(config.getSendUrl());
            //根据请求设置URL
            response = httpClient.execute(httpPost);
            String result = handlerRespone(response, logInfoBean);
            logger.saveLog(logInfoBean);
            dto.putValue("data", result);
            return dto;
        } catch (IOException e) {
            dto.setErrCode(1);
            dto.setMsg("发送短信失败:" + e.getMessage());
            LogUtil.error(LOGGER, "send error:{}", e);
            logInfoBean.setReturnInfo(e.getMessage());
            logger.saveLog(logInfoBean);
            return dto;
        } finally {
            if (!Check.NuNObj(httpClient)) {
                httpClient.close();
            }
            if (!Check.NuNObj(response)) {
                response.close();
            }
        }
    }


    public String handlerRespone(CloseableHttpResponse response, LogInfoBean logInfoBean) throws IOException {
        int status = response.getStatusLine().getStatusCode();
        String resultStr = null;
        if (status == 200) {
            resultStr = EntityUtils.toString(response.getEntity());
        }
        logInfoBean.setReturnCode(status + "");
        logInfoBean.setReturnInfo(resultStr);
        return resultStr;
    }
}
