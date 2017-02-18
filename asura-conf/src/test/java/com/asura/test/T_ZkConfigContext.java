/*
 * Copyright (c) 2016. struggle.2036@163.com. All rights reserved.
 */
package com.asura.test;

import com.asura.framework.conf.subscribe.ConfigSubscriber;
import com.asura.test.pojo.TestNewEnum;
import com.asura.test.pojo.TestOldEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wurt2
 * @since 1.0
 * @version 1.0
 */
public class T_ZkConfigContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(T_ZkConfigContext.class);

    /**
     * 获取自如客根据Token获取URL的地址
     * @return
     */
    public static String getOldZrkInfoByTokenUrl() {
        try {
            String type = TestOldEnum.INFO_BY_TOKEN_URL.getType();
            String code = TestOldEnum.INFO_BY_TOKEN_URL.getCode();
            String value = ConfigSubscriber.getInstance().getConfigValue(type, code);
            if (value == null) {
                value = TestOldEnum.INFO_BY_TOKEN_URL.getDefaultValue();
            }
            return value;
        } catch (Exception e) {
            LOGGER.error("zk config INFO_BY_TOKEN_URL error:{}", e);
            //出NumberFormatException异常了的话使用默认值
            return TestOldEnum.INFO_BY_TOKEN_URL.getDefaultValue();
        }
    }

    /**
     * 获取自如客根据Token获取URL的地址
     * @return
     */
    public static Object getNewZrkInfoByTokenUrl() {
        try {
            String appName = TestNewEnum.INFO_BY_TOKEN_URL.getAppName();
            String type = TestNewEnum.INFO_BY_TOKEN_URL.getType();
            String code = TestNewEnum.INFO_BY_TOKEN_URL.getCode();
            String value = ConfigSubscriber.getInstance().getConfigValue(appName, type, code);
            if (value == null) {
                value = TestOldEnum.INFO_BY_TOKEN_URL.getDefaultValue();
            }
            return value;
        } catch (Exception e) {
            LOGGER.error("zk config INFO_BY_TOKEN_URL error:{}", e);
            //出NumberFormatException异常了的话使用默认值
            return TestNewEnum.INFO_BY_TOKEN_URL.getDefaultValue();
        }
    }
}
