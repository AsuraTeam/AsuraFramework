/**
 * @FileName: CommandMBean.java
 * @Package com.asura.framework.monitor.jmx.mbean
 * 
 * @author zhangshaobin
 * @created 2012-12-31 上午9:56:17
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.monitor.jmx.mbean;

/**
 * <p>命令接口</p>
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
public interface CommandMBean {

	public final static String COMMAND_MXBEAN_NAME = "com.asura:type=Command";

	/**
	 * 
	 * 启动应用
	 *
	 * @author zhangshaobin
	 * @created 2012-12-31 下午1:13:13
	 *
	 * @param path
	 * @return
	 */
	public boolean startApplication(String path);

	/**
	 * 
	 * 停止应用
	 *
	 * @author zhangshaobin
	 * @created 2013-1-11 下午7:05:35
	 *
	 * @param path
	 * @return
	 */
	public boolean stopApplication(String path);

}
