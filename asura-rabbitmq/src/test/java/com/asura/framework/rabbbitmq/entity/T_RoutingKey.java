/**
 * @FileName: T_RoutingKey.java
 * @Package: com.asura.framework.rabbbitmq.entity
 * @author sence
 * @created 3/12/2016 11:20 AM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbbitmq.entity;

import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.entity.RoutingKey;
import com.asura.framework.rabbitmq.exception.AsuraRabbitMqException;
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
public class T_RoutingKey {

    @Test
    public void getRoutingKey() {
        RoutingKey routingKey = new RoutingKey("s", "m", "f");
        Assert.assertEquals(routingKey.getKey(), "s.m.f");
    }

    @Test
    public void getRoutingKey1() {
        try {
            RoutingKey routingKey = new RoutingKey("", "", "");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "RoutingKey:system is null");
        }
    }

    @Test
    public void getRoutingKey2() {
        try {
            RoutingKey routingKey = new RoutingKey("s", "", "");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "RoutingKey:module is null");
        }
    }

    @Test
    public void getRoutingKey3() {
        try {
            RoutingKey routingKey = new RoutingKey("s", "m", "");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "RoutingKey:function is null");
        }
    }

    @Test(expected = AsuraRabbitMqException.class)
    public void getRoutingKey4() {
        QueueName nameKey = new QueueName("{s}", "m", "f");
        Assert.assertEquals(nameKey.getName(), "s_m_f");
    }

}
