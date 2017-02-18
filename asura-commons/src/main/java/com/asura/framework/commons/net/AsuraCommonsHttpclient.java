/**
 * @FileName: AsuraCommonsHttpclient.java
 * @Package: com.asura.framework.commons.net
 * @author liusq23
 * @created 2016/11/29 下午9:14
 * <p>
 * Copyright 2017 Asura
 */
package com.asura.framework.commons.net;

import com.asura.framework.commons.util.Check;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * <p></p>
 * <p>
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
public class AsuraCommonsHttpclient {


    private static Logger LOGGER = LoggerFactory.getLogger(AsuraCommonsHttpclient.class);


    private static AsuraCommonsHttpclient asuraCommonsHttpClient = new AsuraCommonsHttpclient();

    public static AsuraCommonsHttpclient getInstance() {
        return asuraCommonsHttpClient;
    }

    /**
     * 执行get请求
     *
     * @param url
     * @return
     */
    public String doGet(String url) throws IOException {
        return doGet(url, null);
    }

    /**
     * 执行get请求
     *
     * @param url
     * @param header
     * @return
     */
    public String doGet(@NotNull String url, Map<String, String> header) throws IOException {
        return doGet(url, header, 3000, 3000);
    }

    /**
     * 执行get请求 带超时参数
     *
     * @param url
     * @param header
     * @return
     */
    public String doGet(@NotNull String url, Map<String, String> header, int connectTimeout, int socketTimeout) throws IOException {
        Objects.requireNonNull(url, "http url cannot be bull");
        /**
         * 默认的httpclient
         */
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        handlerHeader(header, httpGet);
        handlerRequestConfig(connectTimeout, socketTimeout, httpGet);
        try {
            return httpClient.execute(httpGet, new StringResponseHandler());
        } finally {
            httpClient.close();
        }
    }

    /**
     * @param url
     * @param param
     * @return
     * @throws IOException
     */
    public String doPostForm(@NotNull String url, Map<String, String> param) throws IOException {
        return doPostForm(url, param, null, 3000, 3000);
    }


    /**
     * @param url
     * @param params
     * @param header
     * @return
     * @throws IOException
     */
    public String doPostForm(@NotNull String url, Map<String, String> params, Map<String, String> header) throws IOException {
        return doPostForm(url, params, header, 3000, 3000);
    }

    /**
     * @param url
     * @param params
     * @param header
     * @param connectTimeout
     * @param socketTimeout
     * @return
     * @throws IOException
     */
    public String doPostForm(@NotNull String url, Map<String, String> params, Map<String, String> header, int connectTimeout, int socketTimeout) throws IOException {
        Objects.requireNonNull(url, "http url cannot be bull");
        /**
         * 默认的httpclient
         */
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        handlerHeader(header, httpPost);
        handlerRequestConfig(connectTimeout, socketTimeout, httpPost);
        handlerParam(params, httpPost);
        try {
            return httpClient.execute(httpPost, new StringResponseHandler());
        } finally {
            httpClient.close();
        }
    }

    /**
     * @param url
     * @param jsonString
     * @return
     * @throws IOException
     */
    public String doPostJson(@NotNull String url, String jsonString) throws IOException {
        return doPostJson(url, jsonString, null, 3000, 3000);
    }


    /**
     * @param url
     * @param jsonString
     * @param header
     * @return
     * @throws IOException
     */
    public String doPostJson(@NotNull String url, String jsonString, Map<String, String> header) throws IOException {
        return doPostJson(url, jsonString, header, 3000, 3000);
    }

    /**
     * @param url
     * @param jsonString
     * @param header
     * @param connectTimeout
     * @param socketTimeout
     * @return
     * @throws IOException
     */
    public String doPostJson(@NotNull String url, String jsonString, Map<String, String> header, int connectTimeout, int socketTimeout) throws IOException {
        Objects.requireNonNull(url, "http url cannot be bull");
        /**
         * 默认的httpclient
         */
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        if (Check.isNullOrEmpty(header)) {
            header = new HashMap<>();
            header.put("Content-Type", "application/json");
        }
        handlerHeader(header, httpPost);
        handlerRequestConfig(connectTimeout, socketTimeout, httpPost);
        handlerJsonParam(jsonString, httpPost);
        try {
            return httpClient.execute(httpPost, new StringResponseHandler());
        } finally {
            httpClient.close();
        }
    }

    private void handlerParam(Map<String, String> param, HttpPost httpPost) throws UnsupportedEncodingException {
        if (Check.isNullOrEmpty(param)) {
            return;
        }
        Iterator<String> iterator = param.keySet().iterator();
        List<NameValuePair> nvps = new ArrayList<>(param.size());
        while (iterator.hasNext()) {
            String paramName = iterator.next();
            nvps.add(new BasicNameValuePair(paramName, param.get(paramName)));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
    }

    private void handlerJsonParam(String jsonParam, HttpPost httpPost) throws UnsupportedEncodingException {
        if (Check.isNullOrEmpty(jsonParam)) {
            return;
        }
        httpPost.setEntity(new StringEntity(jsonParam, "UTF-8"));
    }

    /**
     * @param connectTimeout
     * @param socketTimeout
     * @param httpRequestBase
     */
    private void handlerRequestConfig(int connectTimeout, int socketTimeout, HttpRequestBase httpRequestBase) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout).build();
        httpRequestBase.setConfig(requestConfig);
    }

    /**
     * @param header
     * @param httpRequestBase
     */
    private void handlerHeader(Map<String, String> header, HttpRequestBase httpRequestBase) {
        if (Check.isNullOrEmpty(header)) {
            return;
        }
        Iterator<String> iterator = header.keySet().iterator();
        while (iterator.hasNext()) {
            String headerName = iterator.next();
            httpRequestBase.addHeader(headerName, header.get(headerName));
        }
    }

    class StringResponseHandler extends AbstractResponseHandler<String> {

        private String charset;

        private StringResponseHandler() {
            this("UTF-8");
        }

        private StringResponseHandler(String charset) {
            this.charset = charset;
        }

        @Override
        public String handleEntity(HttpEntity entity) throws IOException {
            return EntityUtils.toString(entity, charset);
        }

    }
}
