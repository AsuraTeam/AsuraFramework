/*
 * Copyright (c) 2017 Asura
 */
package com.asura.framework.commons.json;

import com.asura.framework.commons.date.DatePattern;
import com.asura.framework.commons.exception.JsonTransformException;
import com.asura.framework.commons.util.Check;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
 * @author sunzhenlei
 * @version 1.0
 * @date 2016/11/2 17:36
 * @since 1.0
 */
public class Json {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        //设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //当为空字符串的时候 可以反序列化为null
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        //序列化时候为空报错
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        //日期
        objectMapper.setDateFormat(new SimpleDateFormat(DatePattern.DEFAULT_FORMAT_MILLISECOND_PATTERN));
    }

    private Json() {

    }


    /**
     * 将json转换成Object
     *
     * @param json  要转换的json数据
     * @param clazz 目标实体
     * @return Entity    转换后的实体对象
     */
    public static <T> T parseObject(@NotNull String json, @NotNull Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new JsonTransformException("Json转换成Object时出现异常", e);
        }
    }

    /**
     * 将自定义对象转json
     *
     * @param object 待转换的对象实体
     * @return json    转换后的json数据
     */
    public static String toJsonString(@NotNull Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new JsonTransformException("Entity转换成Json时出现异常", e);
        }
    }

    /**
     * json转换list通用
     * <p/>
     * 详细见：http://www.baeldung.com/jackson-collection-array
     * <p/>
     * One final note is that the <T> T class needs to have the no-args default constructor
     * if it doesn’t, Jackson will not be able to instantiate it:
     *
     * @param json  待转换的json串
     * @param clazz 需要转换的类类型
     * @return
     */
    public static <T> List<T> parseObjectList(@NotNull String json, @NotNull Class<T> clazz) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<T>>() {
            });
        } catch (IOException e) {
            throw new JsonTransformException("Json转换成List<T>异常。", e);
        }
    }

    /**
     * 可以取出类似与fastjson JSONObject类似的一个node tree
     * path 为null时候 返回为 root json node
     * 例如：{"a":"a1","b":"b1","c":{"d":"d1","e":"e2"}}
     * 当path为null或者空时 返回jsonnode为：{"a":"a1","b":"b1","c":{"d":"d1","e":"e2"}}
     * 房path为c 返回jsonnode为： {"d":"d1","e":"e2"}
     *
     * @param json  json串
     * @param paths json key 路径path
     * @return
     */
    public static JsonNode parseJsonNode(@NotNull String json, @Nullable String... paths) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            if (!Check.isNullOrEmpty(paths)) {
                for (String pt : paths) {
                    rootNode = rootNode.path(pt);
                    //如果不存在
                    if (rootNode.isMissingNode()) {
                        return null;
                    }
                }
            }
            return rootNode;
        } catch (IOException e) {
            throw new JsonTransformException("Json获取JsonNode时出现异常。", e);
        }
    }

    /**
     * 将json串内的某一个path下的json串转换为 object
     *
     * @param json
     * @param paths
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T getPathObject(@NotNull String json, @NotNull Class<T> t, @Nullable String... paths) {
        JsonNode jsonNode = parseJsonNode(json, paths);
        if (Check.isNull(jsonNode)) {
            return null;
        }
        try {
            return objectMapper.treeToValue(jsonNode, t);
        } catch (JsonProcessingException e) {
            throw new JsonTransformException("将json串内的某一个path下的json串转换为 object 时出现异常。", e);
        } catch (IOException e) {
            throw new JsonTransformException("将json串内的某一个path下的json串转换为 object 时出现异常。", e);
        }
    }


    /**
     * 将json串内的某一个path下的json串转换为 object
     *
     * @param json
     * @param paths
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T getPathObject(@NotNull String json, @NotNull TypeReference typeReference, @Nullable String... paths) {
        String pathJson = getString(json, paths);
        try {
            return objectMapper.readValue(pathJson, typeReference);
        } catch (JsonProcessingException e) {
            throw new JsonTransformException("将json串内的某一个path下的json串转换为 object 时出现异常。", e);
        } catch (IOException e) {
            throw new JsonTransformException("将json串内的某一个path下的json串转换为 object 时出现异常。", e);
        }
    }


    /**
     * 将json串内的某一个path下的json串转换为 List
     *
     * @param json
     * @param t
     * @param paths
     * @param <T>
     * @return
     */
    public static <T> List<T> getPathArray(@NotNull String json, @NotNull Class<T> t, @Nullable String... paths) {
        JsonNode jsonNode = parseJsonNode(json, paths);
        if (Check.isNull(jsonNode)) {
            return null;
        }
        List<T> list = new ArrayList<T>();
        try {
            if (jsonNode.isArray()) {
                for (JsonNode jnode : jsonNode) {
                    list.add(objectMapper.treeToValue(jnode, t));
                }
            }
            return list;
        } catch (JsonProcessingException e) {
            throw new JsonTransformException("将json串内的某一个path下的json串转换为 List 时出现异常。", e);
        } catch (IOException e) {
            throw new JsonTransformException("将json串内的某一个path下的json串转换为 List 时出现异常。", e);
        }
    }

    /**
     * 获取json串内的某一个path下的integer值
     *
     * @param json
     * @param paths
     * @return
     */
    public static Integer getInt(@NotNull String json, @Nullable String... paths) {
        return getPathObject(json, Integer.class, paths);
    }

    /**
     * @param json
     * @param paths
     * @return
     */
    public static Long getLong(@NotNull String json, @Nullable String... paths) {
        return getPathObject(json, Long.class, paths);
    }

    /**
     * @param json
     * @param paths
     * @return
     */
    public static Short getShort(@NotNull String json, @Nullable String... paths) {
        return getPathObject(json, Short.class, paths);
    }

    /**
     * @param json
     * @param paths
     * @return
     */
    public static Byte getByte(@NotNull String json, @Nullable String... paths) {
        return getPathObject(json, Byte.class, paths);
    }


    /**
     * @param json
     * @param paths
     * @return
     */
    public static Float getFloat(@NotNull String json, @Nullable String... paths) {
        return getPathObject(json, Float.class, paths);
    }

    /**
     * @param json
     * @param paths
     * @return
     */
    public static Double getDouble(@NotNull String json, @Nullable String... paths) {
        return getPathObject(json, Double.class, paths);
    }

    /**
     *
     * 获取json传 K-V 的V值只适应于获取叶子节点的V值
     * 注意：如果{"a":"b","c":{"d":"d1","e":{"f","f1"}}}
     * 当 path为c时候,返回：{"d":"d1","e":{"f","f1"}}
     * @param json
     * @param paths
     *
     * @return
     */
    public static String getString(@NotNull String json, @Nullable String... paths) {
        JsonNode jsonNode = parseJsonNode(json,  paths);
        if (Check.isNull(jsonNode)) {
            return null;
        }
        if(jsonNode.isValueNode()){
            return jsonNode.textValue();
        }
        return toJsonString(jsonNode);
    }
}
