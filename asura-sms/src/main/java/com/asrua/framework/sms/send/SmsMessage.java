/**
 * @FileName: SmsMessage.java
 * @Package: com.asrua.framework.sms
 * @author sence
 * @created 10/10/2015 3:17 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asrua.framework.sms.send;

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
public class SmsMessage {
    /**
     * 目的地
     */
    private String toPhone;
    /**
     * 短信内容
     */
    private String content;


    public String getToPhone() {
        return toPhone;
    }

    public void setToPhone(String toPhone) {
        this.toPhone = toPhone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
