/**
 * @FileName: JmxServer.java
 * @Package com.asura.framework.monitor.jmx.server
 * 
 * @author zhangshaobin
 * @created 2012-12-28 下午3:38:44
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.monitor.jmx.server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.exception.BusinessException;

/**
 * <p>JMX服务</p>
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
public class JmxServer {

	private Logger logger = LoggerFactory.getLogger(JmxServer.class);

	/**
	 * MBeanSrver端口
	 */
	private int port = 9020;

	/**
	 * 实例化JmxServer
	 */
	private static JmxServer instance = new JmxServer();

	/**
	 * jdk平台JMXConnectorServer
	 */
	//	private JMXConnectorServer platformConnectorServer;

	/**
	 * 自定义JMXConnectorServer
	 */
	private JMXConnectorServer connectorServer;

	/**
	 * jdk平台MBeanServer
	 */
	//	private MBeanServer platformServer;

	/**
	 * 自定义MBeanServer
	 */
	private MBeanServer server;

	/**
	 * 私有构造器，防止外部实例化
	 */
	private JmxServer() {
		start();
	}

	/**
	 * 
	 * 获取JmxServer实例
	 *
	 * @author zhangshaobin
	 * @created 2012-12-31 上午11:23:58
	 *
	 * @return	JmxServer实例
	 */
	public static JmxServer getInstance() {
		return instance;
	}

	/**
	 * 
	 * 在当前Server上创建MBean
	 *
	 * @author zhangshaobin
	 * @created 2012-12-28 下午4:29:17
	 *
	 * @param className	类名
	 * @param mbeanName	MBean名称
	 * @return	创建成功标识
	 * @throws BusinessException	创建MBean出错时抛出异常
	 */
	public boolean registMBean(String className, String mbeanName) throws BusinessException {
		ObjectInstance instance = null;
		ObjectName objMBeanName = null;
		try {
			objMBeanName = new ObjectName(mbeanName);
		} catch (MalformedObjectNameException e) {
			logger.error("创建MBeanName异常。", e.getCause());
			throw new BusinessException(e.getMessage(), e.getCause());
		}
		try {
			instance = server.createMBean(className, objMBeanName);
		} catch (InstanceAlreadyExistsException e) {
			logger.error("创建MBean异常，实例已经存在。", e.getCause());
			throw new BusinessException("创建MBean异常，实例已经存在。", e.getCause());
		} catch (NotCompliantMBeanException e) {
			logger.error("创建MBean异常，JMX不兼容的MBean类型。", e.getCause());
			throw new BusinessException("创建MBean异常，JMX不兼容的MBean类型。", e.getCause());
		} catch (MBeanRegistrationException e) {
			logger.error("注册MBean异常。", e.getCause());
			throw new BusinessException("注册MBean异常。", e.getCause());
		} catch (ReflectionException e) {
			logger.error("创建MBean调用java.lang.reflect异常。", e.getCause());
			throw new BusinessException("创建MBean调用java.lang.reflect异常。", e.getCause());
		} catch (MBeanException e) {
			logger.error("创建MBean出现异常。", e.getCause());
			throw new BusinessException("创建MBean出现异常。", e.getCause());
		}
		if (null == instance) {
			return false;
		} else {
			System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date()) + "Registed mbean : "
					+ className + ".");
			return instance.getClassName().equals(className);
		}
	}

	/**
	 * 
	 * 为指定的MBean添加NotificationListener
	 *
	 * @author zhangshaobin
	 * @created 2012-12-28 下午4:36:59
	 *
	 * @param mbeanName	MBean名称
	 * @param listener	监听器
	 * @throws BusinessException	MBean实例不存在时抛出异常
	 */
	public void addNotificationListener(String mbeanName, NotificationListener listener) throws BusinessException {
		try {
			ObjectName objMBeanName = null;
			try {
				objMBeanName = new ObjectName(mbeanName);
			} catch (MalformedObjectNameException e) {
				e.printStackTrace();
				throw new BusinessException(e.getMessage(), e.getCause());
			}
			server.addNotificationListener(objMBeanName, listener, null, null);
		} catch (InstanceNotFoundException e) {
			logger.error("注册NotificationListener异常，Mbean实例不存在。", e.getCause());
			throw new BusinessException("注册NotificationListener异常，Mbean实例不存在。", e.getCause());
		}
	}

	/**
	 * 
	 * 启动JMXConnectorServer
	 *
	 * @author zhangshaobin
	 * @created 2012-12-28 下午4:00:59
	 *
	 * @throws IOException
	 */
	private void start() {
		if (null != server)
			return;
		try {
			//			platformServer = ManagementFactory.getPlatformMBeanServer();
			server = MBeanServerFactory.createMBeanServer("Asura");
			JMXServiceURL url = new JMXServiceURL("jmxmp", null, port);
			//			JMXServiceURL platformUrl = new JMXServiceURL("jmxmp", null, 9021);
			//       	platformConnectorServer = JMXConnectorServerFactory.newJMXConnectorServer(platformUrl, null, platformServer);
			//       	platformConnectorServer.start();
			//       	System.out.println("JMX PlatformServer started! Used port 9020.");
			connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, server);
			connectorServer.start();
			System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date())
					+ "JMX Server started! used port:" + port);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date())
					+ "JMX Server started failed!" + " " + e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * 
	 * 初始化MBean
	 *
	 * @author zhangshaobin
	 * @created 2013-1-8 下午6:58:00
	 *
	 */
	@SuppressWarnings("unused")
	private void initMBeans() {
		// 全部使用注解的方式
		//		createMBean("com.sfbest.monitor.jmx.mbean.Command", Constants.COMMAND_MXBEAN_NAME);
		//		registMBean("com.asura.framework.monitor.jmx.mbean.SystemInfo", SystemInfoMBean.SYSTEMINFO_MXBEAN_NAME);
	}

	/**
	 * 
	 * 停止JMXConnectorServer
	 *
	 * @author zhangshaobin
	 * @created 2012-12-28 下午4:08:56
	 *
	 * @throws IOException
	 */
	public void stop() throws IOException {
		if (null != connectorServer) {
			connectorServer.stop();
		}
		//		if (null != platformConnectorServer) {
		//			platformConnectorServer.stop();
		//		}
	}
}
