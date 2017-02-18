/**
 * @FileName: T_RabbitConntectionFactory.java
 * @Package: com.asura.framework.rabbbitmq
 * @author sence
 * @created 3/9/2016 3:02 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbbitmq;

import com.asura.framework.rabbitmq.connection.RabbitConnectionFactory;
import com.rabbitmq.client.Connection;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
public class T_RabbitConnectionFactory {


    @Test
    public void testConnection() throws Exception {
        RabbitConnectionFactory connectionFactory = new RabbitConnectionFactory();
        connectionFactory.init();
        Connection connection = connectionFactory.getConnection();
        Assert.assertNotNull(connection);
    }

}
