/**
 * @FileName: TokenSmsSenderConfig.java
 * @Package: com.asrua.framework.sms.conf
 * @author sence
 * @created 10/10/2015 3:22 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asrua.framework.sms.conf;

/**
 * <p>自如短信配置</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
public class TokenSmsSenderConfig extends AbstractSmsSenderConfig {

    /**
     * token
     */
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
