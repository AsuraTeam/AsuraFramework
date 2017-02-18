/**
 * @FileName: RabbitConnectionFactory.java
 * @Package com.asure.framework.rabbitmq
 * @author zhangshaobin
 * @created 2016年3月1日 上午10:05:54
 * <p/>
 * Copyright 2011-2015 asura
 */
package com.asura.framework.rabbitmq.connection;

import com.asura.framework.rabbitmq.exception.AsuraRabbitMqException;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * 连接工厂创建
 * </p>
 * <p/>
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
public class RabbitConnectionFactory {

    public static final String rabbit_server = "rabbit.server";
    public static final String rabbit_server_port = "rabbit.server.port";
    public static final String rabbit_server_username = "rabbit.server.username";
    public static final String rabbit_server_password = "rabbit.server.password";
    public static final String rabbit_server_automatic_recovery_enabled = "rabbit.server.automatic_recovery_enabled";
    public static final String rabbit_server_env = "rabbit.server.env";
    private final static Logger logger = LoggerFactory.getLogger(RabbitConnectionFactory.class);
    private PropertiesParser cfg;

    private String propSrc = null;

    private ConnectionFactory connectionFactory = null;

    private Connection connection;

    private Address[] addresses;

    /**
     * 初始化
     *
     * @author zhangshaobin
     * @created 2016年3月1日 下午4:34:19
     */
    public void init() {
        initialize("rabbit.properties");
    }

    /**
     * 初始化-解析配置文件
     *
     * @param filename
     * @throws AsuraRabbitMqException
     * @author zhangshaobin
     * @created 2016年3月1日 下午4:34:42
     */
    public void initialize(String filename) throws AsuraRabbitMqException {
        if (cfg != null) {
            return;
        }

        InputStream is = null;
        Properties props = new Properties();

        is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(filename);

        try {
            if (is != null) {
                is = new BufferedInputStream(is);
                propSrc = "the specified file : '" + filename
                        + "' from the class resource path.";
            } else {
                is = new BufferedInputStream(new FileInputStream(filename));
                propSrc = "the specified file : '" + filename + "'";
            }
            props.load(is);
        } catch (IOException ioe) {
            AsuraRabbitMqException initException = new AsuraRabbitMqException(
                    "Properties file: '" + filename + "' could not be read.",
                    ioe);
            throw initException;
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException ignore) {
                }
        }

        initialize(props);
    }

    /**
     * 初始化-解析配置文件
     *
     * @param props
     * @throws AsuraRabbitMqException
     * @author zhangshaobin
     * @created 2016年3月1日 下午4:35:53
     */
    private void initialize(Properties props) throws AsuraRabbitMqException {
        if (propSrc == null) {
            propSrc = "an externally provided properties instance.";
        }

        this.cfg = new PropertiesParser(props);
    }

    public ConnectionFactory getConnectFactory() {
        if (connectionFactory != null) {
            return connectionFactory;
        }
        if (initCheck()) {
            connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(cfg.getStringProperty(addresses[0].getHost()));
            connectionFactory.setPort(cfg.getIntProperty(rabbit_server_port));
            connectionFactory.setUsername(cfg
                    .getStringProperty(rabbit_server_username));
            connectionFactory.setPassword(cfg
                    .getStringProperty(rabbit_server_password));
            connectionFactory
                    .setAutomaticRecoveryEnabled(cfg
                            .getBooleanProperty(rabbit_server_automatic_recovery_enabled));
        }
        return connectionFactory;
    }

    /**
     * 校验配置参数
     *
     * @return
     * @author zhangshaobin
     * @created 2016年3月1日 下午4:36:04
     */
    private boolean initCheck() {
        boolean flag = true;
        if (cfg.getStringProperty(rabbit_server) == null) {
            flag = false;
            String msg_error = "rabbit.server配置不能为空值!!!!";
            //	System.out.println(msg_error);
            logger.error(msg_error);
            return flag;
        }

        if (cfg.getIntProperty(rabbit_server_port) == 0) {
            flag = false;
            String msg_error = "rabbit.server.port配置不能为空值!!!!";
            //	System.out.println(msg_error);
            logger.error(msg_error);
            return flag;
        }
        initAddresses(cfg.getStringProperty(rabbit_server),cfg.getIntProperty(rabbit_server_port));
        if (cfg.getStringProperty(rabbit_server_username) == null) {
            flag = false;
            String msg_error = "rabbit.server.username配置不能为空值!!!!";
            //	System.out.println(msg_error);
            logger.error(msg_error);
            return flag;
        }

        if (cfg.getStringProperty(rabbit_server_password) == null) {
            flag = false;
            String msg_error = "rabbit.server.password配置不能为空值!!!!";
            //	System.out.println(msg_error);
            logger.error(msg_error);
            return flag;
        }
        return flag;
    }

    /**
     *
     */
    private void initAddresses(String hosts,int port) {
        String[] servers = hosts.split(",");
        addresses = new Address[servers.length];
        for(int i=0;i<servers.length;i++){
            addresses[i] = new Address(servers[i],port);
        }
    }

    /**
     * 获取系统所处的环境
     *
     * @return
     */
    public String getEnvironment() {
        if (cfg.getStringProperty(rabbit_server_env) == null) {
            return null;
        }
        return cfg.getStringProperty(rabbit_server_env);
    }

    /**
     * 获取连接
     *
     * @return
     * @throws Exception
     * @throws TimeoutException
     * @author zhangshaobin
     * @created 2016年3月1日 下午4:36:28
     */
    public Connection getConnection() throws Exception {
        if (connection == null || !connection.isOpen()) {
            synchronized (this) {
                if (connection == null || !connection.isOpen()) {
                    connection = getConnectFactory().newConnection(addresses);
                }
            }
        }
        return connection;
    }

    /**
     * 获取通道
     *
     * @param connection
     * @return
     * @throws IOException
     * @author zhangshaobin
     * @created 2016年3月1日 下午4:36:40
     */
    public Channel getChannel(Connection connection) throws IOException {
        return connection.createChannel();
    }

    /**
     * 关闭连接、关闭通道
     *
     * @param channel
     * @throws IOException
     * @throws TimeoutException
     * @author zhangshaobin
     * @created 2016年3月1日 下午4:36:57
     */
    public void closeChannel(Channel channel) throws IOException, TimeoutException {
        if (channel != null) {
            channel.close();
        }

    }

    /**
     * @param
     * @throws IOException
     */
    public void closeConnection() throws IOException {
        if (connection != null) {
            //	System.out.println("mq connection factory close connection");
            connection.close();
        }
    }
}
