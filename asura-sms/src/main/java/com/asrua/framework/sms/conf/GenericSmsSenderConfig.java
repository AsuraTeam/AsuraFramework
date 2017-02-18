/**
 * @FileName: SmsSenderConfig.java
 * @Package: com.asrua.framework.sms.conf
 * @author sence
 * @created 10/10/2015 1:39 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asrua.framework.sms.conf;

/**
 * <p>
 * 第三方短信发送组件配置类
 * 1、account_name
 * 2、password
 * 3、sms_channel
 * 4、log  日志记录实现
 * </p>
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
public class GenericSmsSenderConfig extends AbstractSmsSenderConfig {

    /**
     * 从第三放申请的账户名称
     */
    private String accountName;
    /**
     * 申请的密码
     */
    private String password;


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
