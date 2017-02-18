/**
 * @FileName: HostInfo.java
 * @Package com.asura.framework.cache.redisThree.hostinfo
 * 
 * @author zhangshaobin
 * @created 2014年12月9日 下午4:19:19
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.cache.redisThree.hostinfo;

import java.util.List;

import redis.clients.jedis.JedisShardInfo;

import com.asura.framework.cache.redisThree.helper.Primitive;

/**
 * Redis缓存服务器地址信息及连接超时属性配置
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author YRJ
 * @since 1.0
 * @version 1.0
 */
public class HostInfo {
	/** 默认主机名 */
	private static String HOST = "localhost";

	/** Redis默认端口 */
	private static final int PORT = 6379;

	/** Redis默认超时时间 */
	private static final int TIMEOUT = 2000;

	private String host;

	private int port;

	private int timeout;

	public HostInfo() {
		this(HOST, PORT, TIMEOUT);
	}

	public HostInfo(final String host) {
		this(host, PORT, TIMEOUT);
	}

	public HostInfo(final String host, final int port) {
		this(host, port, TIMEOUT);
	}

	public HostInfo(final String host, final int port, final int timeout) {
		this.host = host;
		this.port = port;
		this.timeout = timeout;
	}

	public HostInfo(final String host, final String port, final String timeout) {
		setHost(host);
		setPort(port);
		setTimeout(timeout);
	}

	public String getHost() {
		return host;
	}

	public void setHost(final String host) {
		this.host = (host == null || host.trim().isEmpty()) ? HOST : host.trim();
	}

	public int getPort() {
		return port;
	}

	public void setPort(final int port) {
		this.port = port;
	}

	public void setPort(final String port) {
		this.setPort(Primitive.parseInt(port, PORT));
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(final int timeout) {
		this.timeout = timeout;
	}

	public void setTimeout(final String timeout) {
		this.setTimeout(Primitive.parseInt(timeout, TIMEOUT));
	}

	public JedisShardInfo createJedisShardInfo() {
		return new JedisShardInfo(host, port, timeout);
	}

	public static HostInfo createHostInfo(final List<String> hosts) {
		final String host = hosts.size() > 0 ? hosts.get(0) : null;
		final String port = hosts.size() > 1 ? hosts.get(1) : null;
		final String timeout = hosts.size() > 2 ? hosts.get(2) : null;
		return new HostInfo(host, port, timeout);
	}
}
