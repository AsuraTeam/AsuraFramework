/**
 * @FileName: BaseRequest.java
 * @Package: com.asura.framework.base.dto
 * @author liusq23
 * @created 2016/12/21 下午4:01
 * <p>
 * Copyright 2017 Asura
 */
package com.asura.framework.base.dto;

/**
 * <p>
 * 所有dto的请求基类
 * 包含每个请求需要携带的 来源，时间戳等信息
 * </p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liusq23
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseRequest {

    /**
     * 数据来源: 请求来源
     */
    private String dataSource;
    /**
     * 时间戳
     */
    private long timestamp;


    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
