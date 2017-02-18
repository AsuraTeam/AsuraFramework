/**
 * @FileName: AbstractHttpSmsSender.java
 * @Package: com.asrua.framework.sms.send
 * @author sence
 * @created 10/10/2015 4:44 PM
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
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
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
public abstract class AbstractHttpSmsSender implements ISmsSender {


    private Logger LOGGER = LoggerFactory.getLogger(AbstractHttpSmsSender.class);


    private SmsSendThreadPool smsSendThreadPool;
    /**
     * 配置
     */
    private AbstractSmsSenderConfig config;


    private ISmsLogger logger;

    public AbstractHttpSmsSender(AbstractSmsSenderConfig config) {
        this.config = config;
        this.logger = config.getLogger();
        this.smsSendThreadPool = config.getThreadPool();
    }

    @Override
    public DataTransferObject send(SmsMessage smsMessage) throws IOException {
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
            HttpPost httpPost = buildPostParam(smsMessage);
            response = httpClient.execute(httpPost);
            String result = HttpUtil.handlerRespone(response, logInfoBean);
            logger.saveLog(logInfoBean);
            dto.putValue("data", result);
            return dto;
        } catch (Exception e) {
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

    protected abstract HttpPost buildPostParam(SmsMessage smsMessage) throws UnsupportedEncodingException, URISyntaxException;

    /**
     * 异步处理
     *
     * @param smsMessage
     * @return
     */
    @Override
    public Future<DataTransferObject> asyncSend(SmsMessage smsMessage) throws URISyntaxException, UnsupportedEncodingException {
        return smsSendThreadPool.sumitTask(smsMessage, this.buildPostParam(smsMessage), config);
    }

    /**
     * 创建URI
     *
     * @return
     */
    public URI buildURIByConfig() throws URISyntaxException {
        URIBuilder builder = new URIBuilder();
        builder.setHost(config.getHost());
        builder.setPort(config.getPort());
        builder.setPath(config.getPath());
        builder.setScheme(config.getProtocol());
        builder.setCharset(Charset.forName(config.getCharset()));
        return builder.build();
    }


    public AbstractSmsSenderConfig getConfig() {
        return config;
    }

    public void setConfig(AbstractSmsSenderConfig config) {
        this.config = config;
    }

    public SmsSendThreadPool getSmsSendThreadPool() {
        return smsSendThreadPool;
    }

    public void setSmsSendThreadPool(SmsSendThreadPool smsSendThreadPool) {
        this.smsSendThreadPool = smsSendThreadPool;
    }
}
