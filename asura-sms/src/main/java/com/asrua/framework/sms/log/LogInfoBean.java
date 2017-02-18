/**
 * @FileName: LogInfoBean.java
 * @Package: com.asrua.framework.sms.log
 * @author sence
 * @created 10/10/2015 8:24 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asrua.framework.sms.log;

import com.asrua.framework.sms.send.SmsMessage;

/**
 * <p></p>
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
public class LogInfoBean {

    /**
     * 消息体
     */
    private SmsMessage smsMessage;
    /**
     * 反馈的状态码
     */
    private String returnCode;
    /**
     * 返回的消息
     */
    private String returnInfo;
    /**
     * 发送时候的URL
     */
    private String url;

    public LogInfoBean() {

    }

    public LogInfoBean(SmsMessage smsMessage, String returnCode, String returnInfo, String url) {
        this.smsMessage = smsMessage;
        this.returnCode = returnCode;
        this.returnInfo = returnInfo;
        this.url = url;
    }

    public SmsMessage getSmsMessage() {
        return smsMessage;
    }

    public void setSmsMessage(SmsMessage smsMessage) {
        this.smsMessage = smsMessage;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(String returnInfo) {
        this.returnInfo = returnInfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
