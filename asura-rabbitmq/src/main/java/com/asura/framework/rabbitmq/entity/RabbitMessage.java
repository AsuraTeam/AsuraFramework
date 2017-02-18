/**
 * @FileName: RabbitMessage.java
 * @Package com.asure.framework.rabbitmq.entity
 * @author zhangshaobin
 * @created 2016年2月25日 下午7:52:49
 * <p/>
 * Copyright 2011-2015 asura
 */
package com.asura.framework.rabbitmq.entity;


import com.asura.framework.commons.json.Json;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>消息体封装</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhangshaobin
 * @version 1.0
 * @since 1.0
 */
public class RabbitMessage implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -7968374551672100496L;

    /**
     * 消息数据
     */
    private String data;

    /**
     * 消息类型
     */
    private String type;

    /**
     * 消息操作
     */
    private String method;

    /**
     * 发送时间串
     */
    private String sendTimeStr = String.valueOf(new Date().getTime());

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the sendTimeStr
     */
    public String getSendTimeStr() {
        return sendTimeStr;
    }

    /**
     * @param sendTimeStr the sendTimeStr to set
     */
    public void setSendTimeStr(String sendTimeStr) {
        this.sendTimeStr = sendTimeStr;
    }

    /**
     * @return
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return
     */
    public String toJsonStr() {
        return Json.toJsonString(this);
    }
}
