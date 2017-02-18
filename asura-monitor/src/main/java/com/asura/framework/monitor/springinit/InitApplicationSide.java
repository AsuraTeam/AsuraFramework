/**
 * @FileName: InitApplicationSide.java
 * @Package com.asura.framework.monitor.springinit
 * 
 * @author zhangshaobin
 * @created 2013-1-9 下午8:48:15
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.monitor.springinit;

import com.asura.framework.monitor.annotation.MBeanAnnotationProcessor;
import com.asura.framework.monitor.jmx.server.JmxServer;
import com.asura.framework.monitor.zkclient.ApplicationZkClient;

/**
 * <p>应用端（即被监控方）初始化</p>
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
public final class InitApplicationSide {

	private InitApplicationSide() {
	}

	/**
	 * 
	 * 初始化
	 *
	 * @author zhangshaobin
	 * @created 2013-1-9 下午8:50:03
	 *
	 */
	public void init() {
		ApplicationZkClient.getInstance();
		JmxServer.getInstance();
		//		RemoteFileServer.getInstance();
		MBeanAnnotationProcessor.getInstance();
	}

}
