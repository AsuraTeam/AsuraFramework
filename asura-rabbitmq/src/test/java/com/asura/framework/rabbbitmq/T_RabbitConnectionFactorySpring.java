/**
 * @FileName: T_RabbitConnectionFactory.java
 * @Package: com.asura.framework.rabbbitmq
 * @author sence
 * @created 3/9/2016 9:38 AM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbbitmq;

import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/spring.xml")
public class T_RabbitConnectionFactorySpring {

    @Resource(name = "rabbitSendClient")
    private RabbitMqSendClient rabbitSendClient;

    @Test
    public void testGetConnection() throws Exception {
        Assert.assertNotNull(rabbitSendClient);
    }


    @Test
    public void testSendQueue2() throws Exception {

        while (true) {
            rabbitSendClient.sendQueue(new QueueName("LSQ", "QUEUE", "02"), "HELLO WORLD +");
        }
    }

}
