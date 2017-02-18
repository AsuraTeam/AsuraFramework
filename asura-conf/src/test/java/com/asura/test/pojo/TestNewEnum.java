/*
 * Copyright (c) 2016. struggle.2036@163.com. All rights reserved.
 */
package com.asura.test.pojo;

import com.asura.framework.conf.subscribe.AsuraSub;

@AsuraSub
public enum TestNewEnum {

    INFO_BY_TOKEN_URL("asura-conf", "hermes-foundation", "info-by-token-url", "xxxxxxx");

    private String appName;
    private String type;
    private String code;
    private Object defaultValue;

    TestNewEnum(String appName, String type, String code, Object defaultValue) {
        this.appName = appName;
        this.type = type;
        this.code = code;
        this.defaultValue = defaultValue;
    }

    public String getAppName() {
        return appName;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

}
