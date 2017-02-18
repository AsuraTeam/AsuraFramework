/**
 * @FileName: HttpClient.java
 * @Package com.asura.framework.web.http
 * @author szl
 * @created 2014年12月15日 下午4:05:56
 * <p>
 * Copyright 2011-2015 asura
 */
package com.asura.framework.base.util;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.commons.net.AsuraCommonsHttpclient;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * http客户端
 * 从之前的扩展自apache 到扩展自 asura-commons
 * 对外提供api不变
 * </p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author szl
 * @version 1.0
 * @since 1.0
 */
public class AsuraHttpClient {

    private final Logger logger = LoggerFactory.getLogger(AsuraHttpClient.class);

    private static AsuraHttpClient instance = new AsuraHttpClient();

    private AsuraHttpClient() {
    }

    public static AsuraHttpClient getInstance() {
        return instance;
    }

    /**
     * get请求
     *
     * @param url
     * @return
     * @author zhangshaobin
     * @created 2014年12月15日 下午4:53:06
     */
    public DataTransferObject get(String url) {
        return get(url, null);
    }

    /**
     * get请求
     *
     * @param url     地址
     * @param headers 头部信息
     * @return
     */
    public DataTransferObject get(String url, Map<String, String> headers) {
        DataTransferObject dto = new DataTransferObject();
        try {
            String result = AsuraCommonsHttpclient.getInstance().doGet(url, headers);
            dto.setErrCode(HttpStatus.SC_OK);
            dto.setMsg("请求成功");
            dto.putValue("data", result);
        } catch (IOException e) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("请求失败");
            logger.error("AsuraHttpClient get error:", e);
        }
        return dto;
    }

    /**
     * post请求
     *
     * @param url
     * @param param
     * @return
     * @author zhangshaobin
     * @created 2014年12月15日 下午4:53:19
     */
    public DataTransferObject post(String url, Map<String, String> param) {
        return post(url, null, param);
    }

    /**
     * post请求
     *
     * @param url     地址
     * @param headers 头部信息
     * @param param   参数
     * @return
     */
    public DataTransferObject post(String url, Map<String, String> headers, Map<String, String> param) {
        DataTransferObject dto = new DataTransferObject();
        try {
            String data = AsuraCommonsHttpclient.getInstance().doPostForm(url, param, headers);
            dto.setCode(HttpStatus.SC_OK);
            dto.putValue("data", data);
            dto.setMsg("请求成功");
            logger.trace("data:" + data);
        } catch (Throwable t) {
            dto.setCode(DataTransferObject.ERROR);
            dto.setMsg("请求失败");
            logger.error("AsuraHttpClient post error . ", t);
        }
        return dto;
    }

    /**
     * post发送JSON请求
     *
     * @param url
     * @param json
     * @return
     */
    public DataTransferObject postJSON(String url, String json) {
        DataTransferObject dto = new DataTransferObject();
        try {
            String data = AsuraCommonsHttpclient.getInstance().doPostJson(url, json);
            dto.putValue("data", data);
            dto.setErrCode(HttpStatus.SC_OK);
            dto.setMsg("请求成功");
            logger.trace("data:" + data);
        } catch (IOException t) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("请求失败");
            logger.error("AsuraHttpClient post error . ", t);
        }
        return dto;

    }
}
