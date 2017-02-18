/**
 * @FileName: T_RabbitMqSendUtil.java
 * @Package: com.asura.framework.rabbbitmq
 * @author sence
 * @created 3/9/2016 9:20 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbbitmq;

import com.asura.framework.rabbbitmq.entity.WRoutingKey;
import com.asura.framework.rabbitmq.PublishSubscribeType;
import com.asura.framework.rabbitmq.connection.RabbitConnectionFactory;
import com.asura.framework.rabbitmq.entity.ExchangeName;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.entity.RoutingKey;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import org.junit.Before;
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
public class T_RabbitMqSendUtil {

    RabbitConnectionFactory connectionFactory;
    QueueName queueName = null;
    RoutingKey routingKey = null;
    ExchangeName exchangeName = null;

    @Before
    public void testBefore() {
        connectionFactory = new RabbitConnectionFactory();
        connectionFactory.init();
        queueName = new QueueName("ares", "schedule", "moveCreateOrderSchedule");
        routingKey = new WRoutingKey("sss", "mmm", "bbb", "fff");
        exchangeName = new ExchangeName("ss", "m", "f");
    }

    @Test
    public void testSendQueue() throws Exception {
        RabbitMqSendClient client = new RabbitMqSendClient();
        client.setRabbitConnectionFactory(connectionFactory);
        int i = 0;
        String s = "";
        while (++i < 10) {
            if (s.length() < 2048) {
                s += i;
            }
            client.sendQueue(queueName, "HELLO WORLD +" + s);
        }
    }

    @Test
    public void testSendQueue2() throws Exception {
        RabbitMqSendClient client = new RabbitMqSendClient();
        client.setRabbitConnectionFactory(connectionFactory);
        int i = 0;
        String s = "";
        while (++i < 100000) {
            if (s.length() < 2048) {
                s += i;
            }
            client.sendQueue(queueName, "HELLO WORLD +" + s);
        }
    }

    @Test
    public void testSendTopic() throws Exception {
        ExchangeName exchangeName = new ExchangeName("ss", "m", "f");
        RabbitMqSendClient client = new RabbitMqSendClient();
        client.setRabbitConnectionFactory(connectionFactory);
        int i = 0;
        String s = "";
        while (++i < 100000) {
            if (s.length() < 2048) {
                s += i;
            }
            if (i % 3 == 1) {
                routingKey.setSystem("aaa");
            }
            if (i % 3 == 2) {
                routingKey.setSystem("bbb");
            }
            if (i % 3 == 0) {
                routingKey.setSystem("ccc");
            }
            client.sendTopic(exchangeName, routingKey, PublishSubscribeType.TOPIC, "hello world " + i);
            System.out.println(i);
        }
    }

}
