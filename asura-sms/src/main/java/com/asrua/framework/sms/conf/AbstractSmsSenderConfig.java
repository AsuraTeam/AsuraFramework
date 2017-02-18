/**
 * @FileName: AbstractSmsSenderConfig.java
 * @Package: com.asrua.framework.sms.conf
 * @author sence
 * @created 10/10/2015 3:24 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asrua.framework.sms.conf;

import com.asrua.framework.sms.log.DefaultTextLogger;
import com.asrua.framework.sms.log.ISmsLogger;
import com.asrua.framework.sms.send.SmsSendThreadPool;

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
public abstract class AbstractSmsSenderConfig {

    public static final String PROTOCOL_HTTP = "http";

    /**
     * 发送短信Url
     */
    private String host;
    /**
     * 端口
     */
    private int port = 80;
    /**
     * 路径
     */
    private String path;
    /**
     * 协议
     */
    private String protocol = "http";
    /**
     * c
     * 日志记录工具
     */
    private ISmsLogger logger;
    /**
     *
     */
    private int threadPoolSize;
    /**
     * 字符编码
     */
    private String charset;

    private SmsSendThreadPool threadPool;

    /**
     * 默认构造 使用text log
     * 默认 线程池数量2个
     */
    public AbstractSmsSenderConfig() {
        this(new DefaultTextLogger(), 2);
    }

    /**
     * @param logger
     * @param threadPoolSize
     */
    public AbstractSmsSenderConfig(ISmsLogger logger, int threadPoolSize) {
        this.logger = logger;
        this.threadPool = new SmsSendThreadPool(threadPoolSize, logger);
    }

    public ISmsLogger getLogger() {
        return logger;
    }

    public void setLogger(ISmsLogger logger) {
        this.logger = logger;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public SmsSendThreadPool getThreadPool() {
        return threadPool;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public String getSendUrl() {
        String pathSeparator = "/";
        if (this.path.startsWith("/")) {
            pathSeparator = "";
        }
        return this.getProtocol() + "://" + this.getHost() + ":" + this.getPort() + pathSeparator + this.getPath();
    }
}
