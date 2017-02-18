package com.asura.framework.conf.subscribe;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunzhenlei
 * @version 1.0
 * @date 2016/8/16 9:04
 * @since 1.0
 */
public interface ApplicationConfiguration {

    /**
     * 注册配置的type、code，以保证数据在发生变化时能及时得到通知
     *
     * @param suffixPath
     *         配置信息类型 appName.type.code
     */
    void registConfig(final String suffixPath, final Object defaultValue) throws Exception;

    /**
     * 注册配置的type、code，以保证数据在发生变化时能及时得到通知
     *
     * @param type
     *         配置信息类型
     * @param code
     *         配置信息编码
     * @param defaultValue
     *         默认值
     */
    void registConfig(final String type, final String code, final Object defaultValue) throws Exception;

    /**
     * 注册配置的type、code，以保证数据在发生变化时能及时得到通知
     *
     * @param appName
     *         系统名称
     * @param type
     *         配置信息类型
     * @param code
     *         配置信息编码
     */
    void registConfig(final String appName, final String type, final String code, final Object defaultValue) throws Exception;

    /**
     * 从zookeeper上获取配置信息
     *
     * @param suffixPath
     *         配置路径
     *
     * @return 配置信息值
     */
    String getConfigValue(final String suffixPath);

    /**
     * 从zookeeper上获取配置信息
     *
     * @param type
     *         配置信息类型
     * @param code
     *         配置信息编码
     *
     * @return 配置信息值
     */
    String getConfigValue(final String type, final String code);

    /**
     * 从zookeeper上获取配置信息
     *
     * @param appName
     *         系统名称
     * @param type
     *         配置信息类型
     * @param code
     *         配置信息编码
     *
     * @return 配置信息值
     */
    String getConfigValue(final String appName, final String type, final String code);

    /**
     * @param key
     *         配置项的key
     * @param defaultValue
     *         如果取回的配置项值为空, 应该返回的默认值
     *
     * @return 配置项的值
     */
    String getString(String key, String defaultValue);

    String getString(String key, String defaultValue, Runnable callback);

    /**
     * @param key
     *         配置项的key
     * @param defaultValue
     *         如果取回的配置项值为空, 应该返回的默认值
     *
     * @return 配置项的值
     */
    int getInt(String key, int defaultValue);

    int getInt(String key, int defaultValue, Runnable callback);

    /**
     * @param key
     *         配置项的key
     * @param defaultValue
     *         如果取回的配置项值为空, 应该返回的默认值
     *
     * @return 配置项的值
     */
    long getLong(String key, long defaultValue);

    long getLong(String key, long defaultValue, Runnable callback);

    /**
     * @param key
     *         配置项的key
     * @param defaultValue
     *         如果取回的配置项值为空, 应该返回的默认值
     *
     * @return 配置项的值
     */
    boolean getBoolean(String key, boolean defaultValue);

    boolean getBoolean(String key, boolean defaultValue, Runnable callback);

    /**
     * Sets an instance-level override. This will trump everything including
     * dynamic properties and system properties. Useful for tests.
     *
     * @param key
     * @param value
     */
    void setOverrideProperty(String key, Object value);

}
