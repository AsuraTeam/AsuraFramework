/**
 * @FileName: PropertiesParser.java
 * @Package com.asure.framework.rabbitmq
 * @author zhangshaobin
 * @created 2016年3月1日 上午10:02:50
 * <p/>
 * Copyright 2011-2015 asura
 */
package com.asura.framework.rabbitmq.connection;

import java.util.Properties;

/**
 * <p>
 * 解析配置文件
 * </p>
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
public class PropertiesParser {

    Properties props = null;

    public PropertiesParser(Properties props) {
        this.props = props;
    }

    /**
     *
     * 获取字符串类型参数值
     *
     * @author zhangshaobin
     * @created 2016年3月1日 下午4:37:46
     *
     * @param name
     * @return
     */
    public String getStringProperty(String name) {
        return getStringProperty(name, null);
    }

    public String getStringProperty(String name, String def) {
        String val = props.getProperty(name, def);
        if (val == null) {
            return def;
        }
        val = val.trim();
        return (val.length() == 0) ? def : val;
    }

    /**
     *
     * 获取boolean类型参数值
     *
     * @author zhangshaobin
     * @created 2016年3月1日 下午4:38:08
     *
     * @param name
     * @return
     */
    public boolean getBooleanProperty(String name) {
        return getBooleanProperty(name, false);
    }

    public boolean getBooleanProperty(String name, boolean def) {
        String val = getStringProperty(name);

        return (val == null) ? def : Boolean.valueOf(val).booleanValue();
    }

    /**
     *
     * 获取整数类型参数值
     *
     * @author zhangshaobin
     * @created 2016年3月1日 下午4:38:39
     *
     * @param name
     * @return
     * @throws NumberFormatException
     */
    public int getIntProperty(String name) throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            throw new NumberFormatException(" null string");
        }

        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

}
