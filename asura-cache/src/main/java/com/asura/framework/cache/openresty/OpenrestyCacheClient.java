/**
 * @FileName: OpenrestyCacheClient.java
 * @Package com.asura.framework.cache.openresty
 * @author zhangshaobin
 * @created 2015年3月27日 下午3:49:29
 * <p>
 * Copyright 2011-2015 asura
 */
package com.asura.framework.cache.openresty;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.commons.net.AsuraCommonsHttpclient;
import com.asura.framework.conf.subscribe.ConfigSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * <p>缓存操作客户端</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class OpenrestyCacheClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenrestyCacheClient.class);

    /**
     * 根据key获取memcache缓存数据,如果没有key对应的值， 返回空字符串， 反之，返回对应的值
     *
     * @param key
     * @return 缓存数据
     * @author zhangshaobin
     * @created 2015年3月30日 下午1:47:12
     */
    public static String getCacheOfMemcache(String key) throws BusinessException {
        String value = null;
        StringBuffer url_part = getMcUrl();
        if (Check.NuNStr(url_part.toString())) {
            throw new BusinessException("getCacheOfMemcache 缓存url为空值");
        }
        StringBuffer url = url_part.append("?action=get&key=").append(key);
        DataTransferObject dto = request(url.toString());
        if (dto != null) {
            if (dto.getCode() == 0) {
                value = String.class.cast(dto.getData().get("data"));
            }
        }
        return value;
    }

    /**
     * 保存mc缓存数据
     * http://192.168.0.31:8888/memcache?action=set&key=a943211&value=zhaoyun&flags=0&expire=1000
     * {"code":0,"msg":"请求成功，返回 code=200","data":{"data":"1"}} 插入成功
     * {"code":0,"msg":"请求成功，返回 code=200","data":{"data":"0"}} 插入失败
     *
     * @param key
     * @param value
     * @param isCompression 是否压缩 true 压缩， false 不压缩
     * @param expire        失效时间
     * @return
     * @author zhangshaobin
     * @created 2015年3月31日 上午11:37:41
     */
    public static boolean setCacheOfMemcache(String key, String value, boolean isCompression, int expire) throws Exception {
        boolean flag = false;
        StringBuffer url_part = getMcUrl();
        if (Check.NuNStr(url_part.toString())) {
            throw new BusinessException("setCacheOfMemcache 缓存url为空值");
        }
        StringBuffer url = null;
        if (isCompression) {
            url = url_part.append("?action=set&key=").append(key).append("&value=").append(URLEncoder.encode(value, "utf-8")).append("&flags=1&expire=").append(expire);
        } else {
            url = url_part.append("?action=set&key=").append(key).append("&value=").append(URLEncoder.encode(value, "utf-8")).append("&flags=0&expire=").append(expire);
        }
        DataTransferObject dto = request(url.toString());
        if (dto != null) {
            if (dto.getCode() == 0) {
                String data = String.class.cast(dto.getData().get("data"));
                if ("1".equals(data)) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 根据key获取redis中的缓存数据,如果没有key对应的值， 返回空字符串， 反之，返回对应的值
     *
     * @param key
     * @return 缓存数据
     * @author zhangshaobin
     * @created 2015年3月30日 下午1:47:48
     */
    public static String getCacheOfRedis(String key) throws BusinessException {
        String value = null;
        StringBuffer url_part = getRedisUrl();
        if (Check.NuNStr(url_part.toString())) {
            throw new BusinessException("getCacheOfRedis 缓存url为空值");
        }
        StringBuffer url = url_part.append("?action=get&key=").append(key);
        DataTransferObject dto = request(url.toString());
        if (dto != null) {
            if (dto.getCode() == 0) {
                value = String.class.cast(dto.getData().get("data"));
            }
        }
        return value;
    }

    /**
     * 根据key、filedkey获取redis中的数据,如果没有key对应的值， 返回空字符串， 反之，返回对应的值
     * http://192.168.0.31:8888/lua_redis?action=hget&key=lvyelvyelvye&field=zhangshaobinzhangshaobin
     *
     * @param key
     * @param filedKey
     * @return 缓存数据
     * @author zhangshaobin
     * @created 2015年3月30日 下午1:48:13
     */
    public static String getCacheOfRedis(String key, String filedKey) throws BusinessException {
        String value = null;
        StringBuffer url_part = getRedisUrl();
        if (Check.NuNStr(url_part.toString())) {
            throw new BusinessException("getCacheOfRedis 缓存url为空值");
        }
        StringBuffer url = url_part.append("?action=hget&key=").append(key).append("&field=").append(filedKey);
        DataTransferObject dto = request(url.toString());
        if (dto != null) {
            if (dto.getCode() == 0) {
                value = String.class.cast(dto.getData().get("data"));
            }
        }
        return value;
    }

    /**
     * 保存redis缓存数据
     * http://192.168.0.31:8888/redis?action=set&key=a943211&value=zhaoyun&flags=0&expire=1000
     * {"code":0,"msg":"请求成功，返回 code=200","data":{"data":"1"}} 插入成功
     * {"code":0,"msg":"请求成功，返回 code=200","data":{"data":"0"}} 插入失败
     *
     * @param key
     * @param value
     * @param isCompression 是否压缩 true 压缩， false 不压缩
     * @param expire        失效时间
     * @return
     * @author zhangshaobin
     * @created 2015年3月31日 上午11:37:41
     */
    public static boolean setCacheOfRedis(String key, String value, boolean isCompression, int expire) throws Exception {
        boolean flag = false;
        StringBuffer url_part = getRedisUrl();
        if (Check.NuNStr(url_part.toString())) {
            throw new BusinessException("setCacheOfRedis 缓存url为空值");
        }
        StringBuffer url = null;
        if (isCompression) {
            url = url_part.append("?action=set&key=").append(key).append("&value=").append(URLEncoder.encode(value, "utf-8")).append("&flags=1&expire=").append(expire);
        } else {
            url = url_part.append("?action=set&key=").append(key).append("&value=").append(URLEncoder.encode(value, "utf-8")).append("&flags=0&expire=").append(expire);
        }
        DataTransferObject dto = request(url.toString());
        if (dto != null) {
            if (dto.getCode() == 0) {
                String data = String.class.cast(dto.getData().get("data"));
                if ("1".equals(data)) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 保存redis缓存数据
     * http://192.168.0.31:8888/redis?action=hset&key=a943211&field=234234&value=zhaoyun&flags=0&expire=1000
     * {"code":0,"msg":"请求成功，返回 code=200","data":{"data":"1"}} 插入成功
     * {"code":0,"msg":"请求成功，返回 code=200","data":{"data":"0"}} 插入失败
     *
     * @param key
     * @param fieldKey
     * @param value
     * @param isCompression 是否压缩 true 压缩， false 不压缩
     * @param expire        失效时间
     * @return
     * @author zhangshaobin
     * @created 2015年3月31日 上午11:37:41
     */
    public static boolean setCacheOfRedis(String key, String fieldKey, String value, boolean isCompression, int expire) throws Exception {
        boolean flag = false;
        StringBuffer url_part = getRedisUrl();
        if (Check.NuNStr(url_part.toString())) {
            throw new BusinessException("setCacheOfRedis 缓存url为空值");
        }
        StringBuffer url = null;
        if (isCompression) {
            url = url_part.append("?action=hset&key=").append(key).append("&field=").append(fieldKey).append("&value=").append(URLEncoder.encode(value, "utf-8")).append("&flags=1&expire=").append(expire);
        } else {
            url = url_part.append("?action=hset&key=").append(key).append("&field=").append(fieldKey).append("&value=").append(URLEncoder.encode(value, "utf-8")).append("&flags=0&expire=").append(expire);
        }
        DataTransferObject dto = request(url.toString());
        if (dto != null) {
            if (dto.getCode() == 0) {
                String data = String.class.cast(dto.getData().get("data"));
                if ("1".equals(data)) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 获取mc缓存配置信息
     * 主要url：http://192.168.0.31:8888/memcache
     *
     * @return
     * @author zhangshaobin
     * @created 2015年3月30日 下午2:04:17
     */
    private static StringBuffer getMcUrl() {
        String type = EnumSysConfig.asura_openresty_mc_cache_url.getType();
        String code = EnumSysConfig.asura_openresty_mc_cache_url.getCode();
        String value = ConfigSubscriber.getInstance().getConfigValue(type, code);
        return new StringBuffer(value);
    }

    /**
     * 获取redis缓存配置信息
     * 主要url：http://192.168.0.31:8888/redis
     *
     * @return
     * @author zhangshaobin
     * @created 2015年3月30日 下午2:04:17
     */
    private static StringBuffer getRedisUrl() {
        String type = EnumSysConfig.asura_openresty_redis_cache_url.getType();
        String code = EnumSysConfig.asura_openresty_redis_cache_url.getCode();
        String value = ConfigSubscriber.getInstance().getConfigValue(type, code);
        return new StringBuffer(value);
    }


    /**
     * 根据utl获取缓存数据
     *
     * @param url
     * @return dto data<data,value>
     * @throws BusinessException
     * @author zhangshaobin
     * @created 2015年3月30日 下午2:27:05
     */
    private static DataTransferObject request(String url) throws BusinessException {
        DataTransferObject dto = new DataTransferObject();
        try {
            String data = AsuraCommonsHttpclient.getInstance().doGet(url, null, 2000, 2000);
            dto.putValue("data", data);
            dto.setMsg("请求成功");
        } catch (IOException e) {
            dto.setErrCode(1);
            dto.setMsg("获取缓存数据异常");
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("error:", e);
            }
            throw new BusinessException("获取缓存数据异常:" + e.getMessage());
        }
        return dto;
    }

}
