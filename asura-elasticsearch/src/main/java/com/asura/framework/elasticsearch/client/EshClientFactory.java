package com.asura.framework.elasticsearch.client;

import com.asura.framework.base.util.Check;
import com.asura.framework.conf.common.CommonConstant;
import com.asura.framework.conf.subscribe.ConfigSubscriber;
import com.asura.framework.elasticsearch.util.EsClientConfig;
import com.google.common.base.Preconditions;
import com.netflix.config.DynamicPropertyFactory;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jiangn18
 * @version 1.0
 * @date 2016/8/29 17:02
 * @since 1.0
 */
public class EshClientFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(EshClientFactory.class);

    // 是否扫描集群
    private static boolean sniff;

    // ES 集群名称
    private static String clusterName;

    // IP地址、端口
    private String[] addresses;

    // 环境信息
    private String env;

    private TransportClient esClient;//ES 客户端对象

    /**
     * ES 客户端连接初始化
     *
     * @return ES客户端对象
     */
    private void init() {
        try {
            String applicationName = DynamicPropertyFactory.getInstance().getStringProperty(CommonConstant.CONFIG_APPLICATION_NAME_KEY, null).get();
            clusterName= ConfigSubscriber.getInstance().getConfigValue(applicationName + "." + EsClientConfig.ELASTICSEARCH_CLUSTERNAME);
            String strAddresses= ConfigSubscriber.getInstance().getConfigValue(applicationName + "." + EsClientConfig.ELASTICSEARCH_ADDRESSES);
            addresses=strAddresses.split(",");
            sniff= Boolean.parseBoolean(ConfigSubscriber.getInstance().getConfigValue(applicationName + "." + EsClientConfig.ELASTICSEARCH_TRANSPORT_SNIFF));
            env = ConfigSubscriber.getInstance().getConfigValue(applicationName + "." + EsClientConfig.ELASTICSEARCH_ENV);
        }catch (Exception e){
            LOGGER.error("加载配置异常",e);
        }
    }

    public TransportClient getClient()   {
        if (esClient == null) {
            synchronized (this) {
                if (esClient == null) {
                    try {
                        //判断配置
                        Preconditions.checkNotNull(clusterName, "es 服务clusterName未配置");
                        Preconditions.checkNotNull(addresses, "es 服务ip未配置");
                        //Preconditions.checkArgument(esPort > 0, "es 服务服务port未配置");
                        //设置集群的名字
                        Settings settings = Settings.settingsBuilder().put("client.node", true).put("cluster.name", clusterName).put("client.transport.sniff", sniff).build();
                        //Settings settings = Settings.settingsBuilder().put("client.transport.sniff", sniff).build();
                        //创建集群client并添加集群节点地址
                        esClient = TransportClient.builder().settings(settings).build();
                        for (String address : addresses) {
                            String[] inetAddress = address.split(":");
                            esClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(inetAddress[0]), new Integer(inetAddress[1])));
                        }
                      }catch (Exception e){
                         LOGGER.error("客户端连接初始化异常",e);
                    }
                }
            }
        }
        return esClient;
    }

    /**
     * 索引名称加上环境变量
     * @param indexName
     * @return
     */
    public String getIndexs(String indexName){
        if (Check.NuNStr(env)) {
            return indexName;
        }
        return indexName + "_" + env;
    }

    public String[] getIndexs(String... indexNames){
        if (Check.NuNStr(env)) {
            return indexNames;
        }
        String[] indexArr = new String[indexNames.length];
        for (int i = 0; i < indexNames.length; i++) {
            indexArr[i] = indexNames[i] + "_" + env;
        }
        return indexArr;
    }

    public TransportClient getEsClient() {
        return esClient;
    }

    public boolean isSniff() {
        return sniff;
    }

    public String getClusterName() {
        return clusterName;
    }

    public String getEnv() {
        return env;
    }
}
