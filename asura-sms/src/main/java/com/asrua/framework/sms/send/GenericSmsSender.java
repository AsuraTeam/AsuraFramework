/**
 * @FileName: GenericSmsSender.java
 * @Package: com.asrua.framework.sms.send
 * @author sence
 * @created 10/11/2015 1:26 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asrua.framework.sms.send;

import com.asrua.framework.sms.conf.GenericSmsSenderConfig;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
public class GenericSmsSender extends AbstractHttpSmsSender {


    /**
     * 通用
     *
     * @param config
     */
    public GenericSmsSender(GenericSmsSenderConfig config) {
        super(config);
    }

    /**
     * post 参数
     *
     * @param smsMessage
     */
    @Override
    protected HttpPost buildPostParam(SmsMessage smsMessage) throws UnsupportedEncodingException, URISyntaxException {
        URI uri = super.buildURIByConfig();
        HttpPost httpPost = new HttpPost(uri);
        GenericSmsSenderConfig config = (GenericSmsSenderConfig) this.getConfig();
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("accountName", config.getAccountName()));
        formparams.add(new BasicNameValuePair("password", config.getPassword()));
        formparams.add(new BasicNameValuePair("toPhone", smsMessage.getToPhone()));
        formparams.add(new BasicNameValuePair("content", smsMessage.getContent()));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, config.getCharset());
        httpPost.setEntity(entity);
        return httpPost;
    }
}
