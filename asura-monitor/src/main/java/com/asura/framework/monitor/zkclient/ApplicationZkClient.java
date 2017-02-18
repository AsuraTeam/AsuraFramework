/**
 * @FileName: ApplicationZkClient.java
 * @Package com.asura.framework.monitor.zkclient
 * 
 * @author zhangshaobin
 * @created 2013-1-8 下午4:46:47
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.monitor.zkclient;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.asura.framework.base.context.ApplicationContext;

/**
 * <p>应用（含服务提供者和消费者）的ZK客户端，使应用能在ZKServer上注册自身为一个临时节点，供监控使用</p>
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
public final class ApplicationZkClient {

	Logger logger = LoggerFactory.getLogger(ApplicationZkClient.class);

	// 定时任务执行器, 专门执行向zk注册失败的连接
	private final ScheduledExecutorService retryExecutor = Executors.newScheduledThreadPool(1);

	// 首次执行的延迟时间
	private final long initialDelay = 10000L;

	// 一次执行终止和下一次执行开始之间的延迟
	private final long delay = 6000L;

	// zk客户端
	private ZkClient client = null;

	// 注册节点的名字
	private String nodeName = "";

	// 提供者和消费者标示
	private String side = "";

	private static ApplicationZkClient applicationZkClient;

	/**
	 * 私有构造器，防止外部初始化 
	 */
	private ApplicationZkClient() {
		start();
	}

	/**
	 * 
	 * 获取ApplicationZkClient对象
	 *
	 * @author zhangshaobin
	 * @created 2013-1-9 下午8:24:39
	 *
	 * @return	ApplicationZkClient对象
	 */
	public static synchronized ApplicationZkClient getInstance() {
		if (null != applicationZkClient)
			return applicationZkClient;
		applicationZkClient = new ApplicationZkClient();
		return applicationZkClient;
	}

	/**
	 * 
	 * 建立zookeeper连接，并以(应用名称+IP)注册当前应用到MgmtMonitor下，注册类型为一个临时节点
	 *
	 * @author zhangshaobin
	 * @created 2013-1-8 下午6:30:49
	 *
	 */
	private void start() {
		String address = ConfigUtils.getProperty("dubbo.registry.address");
		String applicationName = ConfigUtils.getProperty("dubbo.application.name");
		int connectionTimeout = 60000;
		String zookeeperServer = null;
		if (address.startsWith("zookeeper") && address.length() > 20) {
			//			zookeeperServer = address.substring(12, address.length() - 5);
			zookeeperServer = address.replace("\\", "").replace("zookeeper://", "").replace("?backup=", ",");
			try {
				client = new ZkClient(zookeeperServer, connectionTimeout);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("连接Zookeeper服务器超时，时间" + connectionTimeout + "毫秒。", e.getCause());
				System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date()) + applicationName
						+ "连接Zookeeper服务器超时，时间" + connectionTimeout + "毫秒。");
				System.exit(1);
			}
			System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date()) + applicationName
					+ " connected to zookeeper server(" + zookeeperServer + ").");
			logger.info(applicationName + " connected to zookeeper server(" + zookeeperServer + ").");
			if (!client.exists("/MgmtMonitor")) {
				client.createPersistent("/MgmtMonitor");
			}
			String host = getLocalAddress().getHostAddress();
			nodeName = "/MgmtMonitor/" + applicationName + ":" + host;
			side = ApplicationContext.isProviderSide() ? "provider" : "consumer";
			if (client.exists(nodeName)) {
				client.delete(nodeName);// zookeeper消除节点存在延迟，所以先删除这个节点。
			}
			try {
				client.createEphemeral(nodeName, side);
				System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date())
						+ "Zookeeper node “" + nodeName + "” created.");
				logger.info("Zookeeper node “" + nodeName + "” created.");
			} catch (Exception e) {
				System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date())
						+ "节点注册失败   Zookeeper node “" + nodeName + "”!");
				logger.error("节点注册失败   Zookeeper node “" + nodeName + "”!", e);
			}
			// 检测并连接注册中心
			retryExecutor.scheduleWithFixedDelay(new Runnable() {
				@Override
				public void run() {
					if (!client.exists(nodeName)) {
						try {
							client.createEphemeral(nodeName, side);
							System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date())
									+ "重新注册节点成功:" + nodeName);
						} catch (Throwable t) {
							logger.error("节点重新注册失败：", t);
						}
					}
				}
			}, initialDelay, delay, TimeUnit.MILLISECONDS);

		} else {
			System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date())
					+ "Registration center not zookeeper!!!!");
			logger.info("Registration center not zookeeper!!!!");
		}
	}

	private InetAddress getLocalAddress() {
		InetAddress localAddress = null;
		try {
			localAddress = InetAddress.getLocalHost();
			if (isValidAddress(localAddress)) {
				return localAddress;
			}
		} catch (Throwable e) {
			logger.warn("查找IP失败, " + e.getMessage(), e);
		}
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			if (interfaces != null) {
				while (interfaces.hasMoreElements()) {
					try {
						NetworkInterface network = interfaces.nextElement();
						Enumeration<InetAddress> addresses = network.getInetAddresses();
						if (addresses != null) {
							while (addresses.hasMoreElements()) {
								try {
									InetAddress address = addresses.nextElement();
									if (isValidAddress(address)) {
										return address;
									}
								} catch (Throwable e) {
									logger.warn("查找IP失败, " + e.getMessage(), e);
								}
							}
						}
					} catch (Throwable e) {
						logger.warn("查找IP失败, " + e.getMessage(), e);
					}
				}
			}
		} catch (Throwable e) {
			logger.warn("查找IP失败, " + e.getMessage(), e);
		}
		logger.error("未能得到本机Ip地址，将使用127.0.0.1代替。");
		return localAddress;
	}

	private final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

	public final String LOCALHOST = "127.0.0.1";

	public final String ANYHOST = "0.0.0.0";

	private boolean isValidAddress(InetAddress address) {
		if (address == null || address.isLoopbackAddress())
			return false;
		String name = address.getHostAddress();
		return (name != null && !ANYHOST.equals(name) && !LOCALHOST.equals(name) && IP_PATTERN.matcher(name).matches());
	}

}
