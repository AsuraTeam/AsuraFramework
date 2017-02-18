/**
 * @FileName: TopicMessageListener.java
 * @Package: com.asura.framework.rabbbitmq.entity
 * @author sence
 * @created 3/16/2016 5:09 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbbitmq;

import com.asura.framework.rabbitmq.receive.IRabbitMqMessageLisenter;
import com.rabbitmq.client.QueueingConsumer;

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
public class TopicMessageListener implements IRabbitMqMessageLisenter {

    @Override
    public void processMessage(QueueingConsumer.Delivery delivery) {
        System.out.println("[" + delivery.getEnvelope().getRoutingKey() + "][receive:" + new String(delivery.getBody()) + "]");
    }

}
