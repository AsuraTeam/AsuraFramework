/**
 * @FileName: ConfigPublisher.java
 * @Package com.sfbest.config.publish
 * @author zhangshaobin
 * @created 2013-6-26 上午10:50:04
 *
 * Copyright 2011-2015 asura
 */
package com.asura.framework.conf.publish;

import com.asura.framework.base.util.Check;
import com.asura.framework.conf.common.CommonConstant;
import com.asura.framework.conf.common.SerializableSerializer;
import com.netflix.config.DynamicPropertyFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>配置信息发布</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhangshaobin
 * @version 1.0
 * @since 1.0
 */
public class ConfigPublisher {

    final static Logger logger = LoggerFactory.getLogger(ConfigPublisher.class);

    private String applicationName;

    private CuratorFramework client;

    private static ConfigPublisher pub;

    private ConfigPublisher() {
        init();
    }

    /**
     * 获取ConfigPublisher实例
     *
     * @return ConfigPublisher实例
     *
     * @author zhangshaobin
     * @created 2013-6-26 上午10:55:04
     */
    public static synchronized ConfigPublisher getInstance() {
        if (null != pub) return pub;
        pub = new ConfigPublisher();
        return pub;
    }

    /**
     * 初始化
     *
     * @author zhangshaobin
     * @created 2013-6-26 上午10:55:30
     */
    private void init() {
        applicationName = DynamicPropertyFactory.getInstance().getStringProperty(CommonConstant.CONFIG_APPLICATION_NAME_KEY, null).get();
        String zkConfigEnsemble = DynamicPropertyFactory.getInstance().getStringProperty(CommonConstant.ZK_ENSEMABLE_KEY, null).get();
        Integer zkConfigSessionTimeout = DynamicPropertyFactory.getInstance().getIntProperty(CommonConstant.ZK_SESSION_TIMEOUT_KEY, 15000).get();
        Integer zkConfigConnTimeout = DynamicPropertyFactory.getInstance().getIntProperty(CommonConstant.ZK_CONN_TIMEOUT_KEY, 5000).get();

        if (Check.NuNStr(zkConfigEnsemble)) {
            logger.warn("ZooKeeper configuration running in file mode, zk is not enabled since not configured");
            return;
        }

        try {
            client = createAndStartZKClient(zkConfigEnsemble, zkConfigSessionTimeout, zkConfigConnTimeout);

            if (client.getState() != CuratorFrameworkState.STARTED) {
                throw new RuntimeException("ZooKeeper located at " + zkConfigEnsemble + " is not started.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("连接配置中心服务器超时，时间5000毫秒。", e.getCause());
            System.exit(1);
        }
        System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date()) + applicationName + " connected to cofnig server(" + zkConfigEnsemble + ").");
        logger.info(applicationName + " connected to cofnig server(" + zkConfigEnsemble + ").");
    }

    /**
     * 创建连接
     *
     * @param connectString
     *
     * @return
     */
    private synchronized static CuratorFramework createAndStartZKClient(String connectString, Integer zkSessionTimeout, Integer zkConnTimeout) {

        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, zkSessionTimeout, zkConnTimeout, new ExponentialBackoffRetry(1000, 3));

        client.start();

        logger.info("Created, started, and cached zk client [{}] for connectString [{}]", client, connectString);

        return client;
    }

    /**
     * 增加配置信息
     *
     * @param type
     *         配置信息类型
     * @param code
     *         配置信息编码
     * @param data
     *         配置信息值
     *
     * @author zhangshaobin
     * @created 2013-6-27 下午3:04:08
     */
    public void setConfig(String type, String code, String data) {
        setConfig(null, type, code, data);
    }

    /**
     * 增加配置信息
     *
     * @param appName
     *         配置信息所属系统
     * @param type
     *         配置信息类型
     * @param code
     *         配置信息编码
     * @param data
     *         配置信息值
     *
     * @author zhangshaobin
     * @created 2013-6-27 下午3:04:08
     */
    public void setConfig(String appName, String type, String code, Object data) {
        String dataPath = CommonConstant.ZK_ROOT_PATH;
        if (Check.NuNStr(appName)) {
            dataPath += "/" + type + "/" + code;
        } else {
            dataPath += "/" + appName + "/" + type + "/" + code;
        }
        try {
            if (!isExistsNode(dataPath)) {
                client.create().creatingParentsIfNeeded().forPath(dataPath);
            }

            client.setData().forPath(dataPath, SerializableSerializer.serialize(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除配置信息
     *
     * @param type
     *         配置信息类型
     * @param code
     *         配置信息编码
     *
     * @author zhangshaobin
     * @created 2013-6-27 下午3:04:23
     */
    public void deleteConfig(String type, String code) {
        deleteConfig(null, type, code);
    }

    /**
     * 删除配置信息
     *
     * @param appName
     *         配置信息所属系统
     * @param type
     *         配置信息类型
     * @param code
     *         配置信息编码
     *
     * @author zhangshaobin
     * @created 2013-6-27 下午3:04:23
     */
    public void deleteConfig(String appName, String type, String code) {
        String dataPath = CommonConstant.ZK_ROOT_PATH;
        if (Check.NuNStr(appName)) {
            dataPath += "/" + type + "/" + code;
        } else {
            dataPath += "/" + appName + "/" + type + "/" + code;
        }
        try {
            if (isExistsNode(dataPath)) {
                client.delete().guaranteed().deletingChildrenIfNeeded().forPath(dataPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从zookeeper上获取配置信息
     *
     * @param type
     *         配置信息类型
     * @param code
     *         配置信息编码
     *
     * @return 配置信息值
     *
     * @author zhangshaobin
     * @created 2013-6-28 下午1:56:52
     */
    public String getConfigValue(String type, String code) {
        return (String) getConfigValue(null, type, code);
    }

    /**
     * 从zookeeper上获取配置信息
     *
     * @param appName
     *         配置信息所属系统
     * @param type
     *         配置信息类型
     * @param code
     *         配置信息编码
     *
     * @return 配置信息值
     *
     * @author zhangshaobin
     * @created 2013-6-28 下午1:56:52
     */
    public Object getConfigValue(String appName, String type, String code) {
        String dataPath = CommonConstant.ZK_ROOT_PATH;
        if (Check.NuNStr(appName)) {
            dataPath += "/" + type + "/" + code;
        } else {
            dataPath += "/" + appName + "/" + type + "/" + code;
        }
        try {
            if (!isExistsNode(dataPath)) {
                return "配置项不存在";
            }
            return SerializableSerializer.deserialize(client.getData().forPath(dataPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断一个节点是否存在
     *
     * @param path
     *         节点路径
     *
     * @throws Exception
     */
    private boolean isExistsNode(String path) throws Exception {
        Stat stat = client.checkExists().forPath(path);
        return null != stat;
    }
}
