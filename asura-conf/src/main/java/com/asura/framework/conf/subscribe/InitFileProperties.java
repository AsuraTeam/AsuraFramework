package com.asura.framework.conf.subscribe;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.PropertiesUtil;
import com.asura.framework.conf.common.CommonConstant;
import com.netflix.config.DynamicPropertyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

/**
 * <p>初始化文件</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunzhenlei
 * @version 1.0
 * @date 2016/9/16 15:45
 * @since 1.0
 */
public class InitFileProperties {

    private final static Logger LOGGER = LoggerFactory.getLogger(InitFileProperties.class);

    private static InitFileProperties initFileProperties;

    private InitFileProperties() {
        init();
    }

    public static synchronized InitFileProperties getInstance() {
        if (null != initFileProperties) return initFileProperties;
        initFileProperties = new InitFileProperties();
        return initFileProperties;
    }

    public void init() {
        String applicationName = DynamicPropertyFactory.getInstance().getStringProperty(CommonConstant.CONFIG_APPLICATION_NAME_KEY, null).get();
        String propertiesNames = DynamicPropertyFactory.getInstance().getStringProperty(CommonConstant.PROPERTIES_RESOURCES_NAME_KEY, null).get();

        if (Check.NuNObj(propertiesNames)) {
            return;
        }

        String[] propertiesNamesArr = propertiesNames.split(",");

        PropertiesUtil propertiesUtil = new PropertiesUtil(propertiesNamesArr);

        Iterator<Map.Entry<Object, Object>> allProperties = propertiesUtil.getAllProperties();

        if (Check.NuNObj(allProperties)) {
            return;
        }

        while (allProperties.hasNext()) {
            Map.Entry<Object, Object> objectEntry = allProperties.next();
            String key = (String) objectEntry.getKey();

            // 配置文件的key默认加上系统名称变量
            if (!Check.NuNStr(applicationName)) {
                key = applicationName + "." + key;
            }

            // 判断System中是否存在相应属性，如存在将不处理
            if (!Check.NuNObj(System.getProperty(key))) {
                continue;
            }

            // 注册ZK监听
            try {
                ConfigSubscriber.getInstance().registConfig(key, objectEntry.getValue());
            } catch (Exception e) {
                LOGGER.error("注册配置文件的属性到ZK监听异常。key:{},error:{}", key, e);
            }
        }
    }
}
