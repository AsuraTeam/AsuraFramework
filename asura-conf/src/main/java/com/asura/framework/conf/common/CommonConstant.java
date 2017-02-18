package com.asura.framework.conf.common;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunzhenlei
 * @version 1.0
 * @date 2016/9/16 16:36
 * @since 1.0
 */
public class CommonConstant {

    public static final String ZK_ROOT_PATH = "/AsuraConfig";
    public static final String CONFIG_APPLICATION_NAME_KEY = "config.application.name";
    public static final String ZK_ENSEMABLE_KEY = "zookeeper.config.ensemble";
    public static final String ZK_SESSION_TIMEOUT_KEY = "zookeeper.session.timeout";
    public static final String ZK_CONN_TIMEOUT_KEY = "zookeeper.connection.timeout";
    public static final String PROPERTIES_RESOURCES_NAME_KEY = "properties.resources.name";
    public static final String AUTO_SCAN_CLASS_PACKAGE_PREFIX_KEY = "auto.scan.class.package.prefix";

    private CommonConstant() {
    }
}
