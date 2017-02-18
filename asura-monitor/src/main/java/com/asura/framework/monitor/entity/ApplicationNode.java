/**
 * @FileName: ApplicationNode.java
 * @Package com.asura.framework.monitor.entity
 * 
 * @author zhangshaobin
 * @created 2012-12-28 下午6:24:44
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.monitor.entity;

import java.io.Serializable;

/**
 * <p>应用节点信息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class ApplicationNode implements Serializable {

	private static final long serialVersionUID = -853155851105987423L;

	/**
	 * 节点IP
	 */
	private String ip;

	/**
	 * 节点应用名称
	 */
	private String applicationName;

	/**
	 * 节点存活状态
	 */
	private boolean alive = true;

	/**
	 * 节点类型
	 */
	private String side;

	/**
	 * 构造器
	 */
	public ApplicationNode() {
	}

	/**
	 * 
	 * 构造器
	 * 
	 * @param ip	节点IP
	 * @param applicationName	节点应用名称
	 */
	public ApplicationNode(String ip, String applicationName, String side) {
		this.ip = ip;
		this.applicationName = applicationName;
		this.side = side;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the applicationName
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * @param applicationName the applicationName to set
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * @return the alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * @param alive the alive to set
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	/**
	 * @return the side
	 */
	public String getSide() {
		return side;
	}

	/**
	 * @param side the side to set
	 */
	public void setSide(String side) {
		this.side = side;
	}

}
