/**
 * @FileName: DataTransferObjectJsonParser.java
 * @Package: com.asura.framework.base.util
 * @author liusq23
 * @created 2016/11/8 下午1:48
 * <p>
 * Copyright 2017 Asura
 */
package com.asura.framework.base.util;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.exception.SystemException;
import com.asura.framework.commons.json.Json;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * DataTransferObject对应json解析
 * 提供SOAResParserUtil 相同的功能
 * </p>
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
public class DataTransferObjectJsonParser {

    /**
     * 私有化构造，工具类
     */
    private DataTransferObjectJsonParser() {

    }

    /**
     * 获取返回的JSON对象
     *
     * @param result
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午5:59:58
     */
    public static JsonNode getJsonNode(@NotNull String result) {
        Objects.requireNonNull(result, "result json must not null");
        return Json.parseJsonNode(result, null);
    }

    /**
     * 获取返回JSON对象的响应状态码
     *
     * @param result
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:01:09
     */
    public static int getReturnCode(final String result) {
        final JsonNode jsonNode = getJsonNode(result);
        if (jsonNode.get("code").isMissingNode()) {
            throw new SystemException("json must be serialized from DataTransferObject ");
        }
        return jsonNode.get("code").intValue();
    }

    /**
     * 获取返回JSON对象的响应信息
     *
     * @param result
     * @created 2014年5月7日 下午6:01:09
     */
    public static String getReturnMsg(String result) {
        final JsonNode jsonNode = getJsonNode(result);
        if (jsonNode.get("msg").isMissingNode()) {
            return null;
        }
        return jsonNode.get("msg").textValue();
    }

    /**
     * 判断SOA返回结果是否符合期望结果,使用默认期望code 0
     *
     * @param result
     * @return
     * @author liushengqi
     * @created 2014年5月14日 上午9:50:06
     */
    public static void assertSOAReturnSuccess(String result) {
        int returnCode = getReturnCode(result);
        switch (returnCode) {
            case DataTransferObject.BUS_SUCCESS:
                return;
            case DataTransferObject.SUCCESS:
                return;
            case DataTransferObject.SYS_ERROR:
                throw new SystemException(Json.getString(result, "msg"));
            default:
                throw new BusinessException(returnCode, Json.getString(result, "msg"));
        }
    }

    /**
     * 获取返回对象中的data对象，需要SOA响应状态码是0
     *
     * @param result
     * @return
     * @author liusq23
     * @created 2014年5月9日 上午11:22:17
     */
    public static JsonNode getDataObj(@NotNull String result) {
        assertSOAReturnSuccess(result);
        return Json.parseJsonNode(result, "data");
    }

    /**
     * 根据key获取返回对象data中的字符串值(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static String getStrFromDataByKey(final String result, final String key) {
        assertSOAReturnSuccess(result);
        return Json.getString(result, "data", key);
    }

    /**
     * 根据key获取返回对象data中的整数(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static Integer getIntFromDataByKey(final String result, final String key) {
        assertSOAReturnSuccess(result);
        return Json.getInt(result, "data", key);
    }

    /**
     * 根据key获取返回对象data中的长整数(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static Long getLongFromDataByKey(final String result, final String key) {
        assertSOAReturnSuccess(result);
        return Json.getLong(result, "data", key);
    }

    /**
     * 根据key获取返回对象data中的float(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static Float getFloatFromDataByKey(final String result, final String key) {
        assertSOAReturnSuccess(result);
        return Json.getFloat(result, "data", key);
    }

    /**
     * 根据key获取返回对象data中的double(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static Double getDoubleFromDataByKey(final String result, final String key) {
        assertSOAReturnSuccess(result);
        return Json.getDouble(result, "data", key);
    }


    /**
     * 根据key获取返回对象data中的JSON对象(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static JsonNode getJsonNodeFromDataByKey(final String result, final String key) {
        assertSOAReturnSuccess(result);
        return Json.parseJsonNode(result, "data", key);
    }

    /**
     * 根据key获取返回对象data中的Array对象(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static ArrayNode getArrayFromDataByKey(final String result, final String key) {
        assertSOAReturnSuccess(result);
        JsonNode jsonNode = Json.parseJsonNode(result, "data", key);
        if (!com.asura.framework.commons.util.Check.isNull(jsonNode) && jsonNode.isArray()) {
            return (ArrayNode) jsonNode.get(key);
        }
        throw new SystemException("json by key " + key + " is not a array");
    }


    /**
     * 从SOA内直接取得对应的class
     *
     * @param result
     * @param key
     * @param clazz
     * @return
     * @throws SOAParseException
     * @author liushengqi
     * @created 2014年5月14日 上午9:58:18
     */
    public static <T> T getValueFromDataByKey(final String result, final String key, Class<T> clazz) {
        assertSOAReturnSuccess(result);
        return Json.getPathObject(result, clazz, "data", key);
    }

    /**
     * 从SOA内直接取得对应的class
     *
     * @param result
     * @param key
     * @param typeReference
     * @return
     * @throws SOAParseException
     * @author liushengqi
     * @created 2014年5月14日 上午9:58:18
     */
    public static <T> T getValueFromDataByKey(final String result, final String key, TypeReference typeReference) {
        assertSOAReturnSuccess(result);
        return Json.getPathObject(result, typeReference, "data", key);
    }

    /**
     * 从SOA内直接取得对应的class list
     *
     * @param result
     * @param key
     * @param clazz
     * @return
     * @throws SOAParseException
     * @author liushengqi
     * @created 2014年5月14日 上午10:19:52
     */
    public static <T> List<T> getListValueFromDataByKey(final String result, final String key, Class<T> clazz) {
        assertSOAReturnSuccess(result);
        return Json.getPathArray(result, clazz, "data", key);
    }

}
