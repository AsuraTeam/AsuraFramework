/**
 * @FileName: SmsReceiveMessage.java
 * @Package: com.asrua.framework.sms.entity
 * @author sence
 * @created 12/3/2015 10:49 AM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asrua.framework.sms.entity;

/**
 * <p>上行短信接口</p>
 * <p>
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
public class SmsReceiveMessage {

    /**
     * 流水号
     */
    private String serial_no;
    /**
     * 回复人
     */
    private String replier;
    /**
     * 回复的内容
     */
    private String content;
    /**
     * 回复时间，时间戳(秒)
     */
    private String reply_time;


    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getReplier() {
        return replier;
    }

    public void setReplier(String replier) {
        this.replier = replier;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply_time() {
        return reply_time;
    }

    public void setReply_time(String reply_time) {
        this.reply_time = reply_time;
    }
}
