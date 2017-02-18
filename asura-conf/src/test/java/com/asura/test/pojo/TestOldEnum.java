/*
 * Copyright (c) 2016. struggle.2036@163.com. All rights reserved.
 */
package com.asura.test.pojo;

import com.asura.framework.conf.subscribe.AsuraSub;

@AsuraSub
public enum TestOldEnum {

    INFO_BY_TOKEN_URL("hermes-foundation", "info-by-token-url", "xxxxxx", "Token获取用户信息地址");

    private String type;
    private String code;
    private String defaultValue;
    private String notes;

    TestOldEnum(String type, String code, String defaultValue, String notes) {
        this.type = type;
        this.code = code;
        this.defaultValue = defaultValue;
        this.notes = notes;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getNotes() {
        return notes;
    }
}
