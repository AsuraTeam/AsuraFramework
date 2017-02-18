/**
 * @FileName: T_BindingKey.java
 * @Package: com.asura.framework.rabbbitmq.entity
 * @author sence
 * @created 3/12/2016 11:20 AM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbbitmq.entity;

import com.asura.framework.rabbitmq.entity.BindingKey;
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
public class T_BindingKey {

    @Test
    public void getBindingKey() {
        BindingKey nameKey = new WBindKey("s", "m", "b", "f");
        Assert.assertEquals(nameKey.getKey(), "s.m.b.f");
    }

    @Test
    public void getBindingKey1() {
        BindingKey nameKey = new BindingKey("", "", "");
        Assert.assertEquals(nameKey.getKey(), "*.*.*");
    }

    @Test
    public void getBindingKey2() {
        BindingKey nameKey = new BindingKey("s", "", "");
        Assert.assertEquals(nameKey.getKey(), "s.*.*");
    }

    @Test
    public void getBindingKey3() {
        BindingKey nameKey = new BindingKey("s", "m", "");
        Assert.assertEquals(nameKey.getKey(), "s.m.*");
    }

    @Test
    public void getBindingKey4() {
        BindingKey nameKey = new BindingKey("s", "", "f");
        Assert.assertEquals(nameKey.getKey(), "s.*.f");
    }

    @Test(expected = AsuraRabbitMqException.class)
    public void getBindingKey5() {
        QueueName nameKey = new QueueName("", "{m}", "f");
        Assert.assertEquals(nameKey.getName(), "s_m_f");
    }

}
