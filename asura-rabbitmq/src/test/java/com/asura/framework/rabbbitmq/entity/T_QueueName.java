/**
 * @FileName: T_QueueName.java
 * @Package: com.asura.framework.rabbbitmq.entity
 * @author sence
 * @created 3/12/2016 11:20 AM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbbitmq.entity;

import com.asura.framework.rabbitmq.entity.QueueName;
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
public class T_QueueName {

    @Test
    public void getQueueName() {
        QueueName nameKey = new QueueName("s", "m", "f");
        Assert.assertEquals(nameKey.getName(), "s_m_f");
    }

    @Test
    public void getQueueName1() {
        try {
            QueueName nameKey = new QueueName("", "", "");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "queueName:system is null");
        }
    }

    @Test
    public void getQueueName2() {
        try {
            QueueName nameKey = new QueueName("s", "", "");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "queueName:module is null");
        }
    }

    @Test
    public void getQueueName3() {
        try {
            QueueName nameKey = new QueueName("s", "m", "");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "queueName:function is null");
        }
    }

    @Test(expected = AsuraRabbitMqException.class)
    public void getQueueName4() {
        QueueName nameKey = new QueueName("{s}", "m", "f");
        Assert.assertEquals(nameKey.getName(), "s_m_f");
    }

}
