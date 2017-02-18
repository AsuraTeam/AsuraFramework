/**
 * @FileName: MonitorZkClient.java
 * @Package com.asura.framework.monitor.zkclient
 * 
 * @author zhangshaobin
 * @created 2013-1-8 下午6:00:07
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.monitor.zkclient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkTimeoutException;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.monitor.entity.ApplicationNode;
import com.asura.framework.monitor.listener.ApplicationNodeListener;

/**
 * <p>监控端的ZK客户端，使自身能在ZKServer上订阅应用（含服务提供者及消费者）注册的临时节点的变化</p>
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
public final class MonitorZkClient {

	/**
	 * ZkClient
	 */
	private ZkClient client = null;

	/**
	 * 实例化MonitorZkClient
	 */
	private static MonitorZkClient monitorZkClient = new MonitorZkClient();

	/**
	 * 存放应用节点监听的集合
	 */
	private List<ApplicationNodeListener> applicationNodeListeners = new ArrayList<>();

	/**
	 * 私有构造器，防止外部序列化
	 */
	private MonitorZkClient() {
		start();
	}

	/**
	 * 
	 * 获取MonitorZkClient实例
	 *
	 * @author zhangshaobin
	 * @created 2013-1-9 下午7:59:15
	 *
	 * @return
	 */
	public static MonitorZkClient getInstance() {
		return monitorZkClient;
	}

	/**
	 * 
	 * 连接ZKServer，初始化ZKClient，注册监听
	 *
	 * @author zhangshaobin
	 * @created 2013-1-8 下午7:02:31
	 *
	 */
	private void start() {
		String address = ConfigUtils.getProperty("dubbo.registry.address");
		String zookeeperServer = null;
		if (address.startsWith("zookeeper") && address.length() > 20) {
			int connectionTimeout = 60000;
			//			zookeeperServer = address.substring(12, address.length() - 5);
			zookeeperServer = address.replace("\\", "").replace("zookeeper://", "").replace("?backup=", ",");
			try {
				client = new ZkClient(zookeeperServer, connectionTimeout);
			} catch (ZkTimeoutException e) {
				e.printStackTrace();
				throw new BusinessException("连接Zookeeper服务器超时，时间" + connectionTimeout + "毫秒。", e.getCause());
			}
			if (!client.exists("/MgmtMonitor")) {
				client.createPersistent("/MgmtMonitor");
			}

			client.subscribeStateChanges(new IZkStateListener() {
				@Override
				public void handleStateChanged(KeeperState state) throws Exception {
					if (state == KeeperState.Disconnected) {
						System.out.println("Disconnected");
					} else if (state == KeeperState.SyncConnected) {
						System.out.println("SyncConnected");
					}
				}

				@Override
				public void handleNewSession() throws Exception {
					System.out.println("handleNewSession");
				}
			});

			//订阅子节点变化
			client.subscribeChildChanges("/MgmtMonitor", new IZkChildListener() {
				@Override
				public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
					if (applicationNodeListeners != null && applicationNodeListeners.size() == 0) {
						for (ApplicationNodeListener listener : applicationNodeListeners) {
							listener.nodeChange(new ArrayList<ApplicationNode>());
						}
					} else {
						for (ApplicationNodeListener listener : applicationNodeListeners) {
							listener.nodeChange(parseNode(currentChilds));
						}
					}
				}
			});
			//输出成功信息
			System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date())
					+ "Monitor connected to zookeeper server(" + zookeeperServer + ")!");
		} else {
			System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date())
					+ "Registration center not zookeeper!");
		}
	}

	/**
	 * 
	 * 将节点在ZK上的注册路径转换为ApplicationNode对象
	 *
	 * @author zhangshaobin
	 * @created 2013-1-9 下午6:51:52
	 *
	 * @param currentChilds	节点在ZKServer
	 * @return	ApplicationNode对象集合
	 */
	private List<ApplicationNode> parseNode(List<String> currentChilds) {
		List<ApplicationNode> list = new ArrayList<>();
		String[] temps;
		for (String path : currentChilds) {
			temps = path.split(":");
			list.add(new ApplicationNode(temps[1], temps[0], (String) client.readData("/MgmtMonitor/" + path)));
		}
		return list;
	}

	/**
	 * 
	 * 添加应用节点监听
	 *
	 * @author zhangshaobin
	 * @created 2013-1-9 下午6:48:47
	 *
	 * @param listener	应用节点监听
	 */
	public void addApplicationNodeListener(ApplicationNodeListener listener) {
		applicationNodeListeners.add(listener);
	}

	/**
	 * 
	 * 查询所有应用节点
	 *
	 * @author zhangshaobin
	 * @created 2013-1-10 下午1:14:59
	 *
	 * @return	节点对象（ApplicationNode）集合
	 */
	public List<ApplicationNode> getAllApplicationNodes() {
		if (client.exists("/MgmtMonitor")) {
			return parseNode(client.getChildren("/MgmtMonitor"));
		}
		return null;
	}

}
