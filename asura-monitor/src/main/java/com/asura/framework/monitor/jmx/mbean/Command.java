/**
 * @FileName: Command.java
 * @Package com.asura.framework.monitor.jmx.mbean
 * 
 * @author zhangshaobin
 * @created 2012-12-31 上午9:55:06
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.monitor.jmx.mbean;

import java.io.IOException;

/**
 * <p>命令信息</p>
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
public class Command implements CommandMBean {

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.CommandMBean#startServerCenter(java.lang.String)
	 */
	@Override
	public boolean startApplication(String path) {
		String os = System.getProperties().getProperty("os.name");
		try {
			if (os.toLowerCase().startsWith("win")) {
				Runtime.getRuntime().exec("cmd /k start " + path + "/bin/start.bat");
			} else {
				Runtime.getRuntime().exec("sh " + path + "/bin/start.sh");
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.jmx.mbean.CommandMBean#stopApplication(java.lang.String)
	 */
	@Override
	public boolean stopApplication(String path) {
		String os = System.getProperties().getProperty("os.name");
		try {
			if (os.toLowerCase().startsWith("win")) {
				Runtime.getRuntime().exec("cmd /k stop " + path + "/bin/start.bat");
			} else {
				Runtime.getRuntime().exec("sh " + path + "/bin/stop.sh");
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
