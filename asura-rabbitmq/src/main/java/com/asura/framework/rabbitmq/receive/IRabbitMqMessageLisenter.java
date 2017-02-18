/**
 * @FileName: IWorker.java
 * @Package com.asure.framework.rabbitmq.receive
 * @author zhangshaobin
 * @created 2016年3月1日 下午2:02:02
 * <p/>
 * Copyright 2011-2015 asura
 */
package com.asura.framework.rabbitmq.receive;

import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 * <p>
 *     消息监听器接口
 *     实现此接口需要实现具体的消息处理机制
 *     注意:此类会多线程调用，请确保线程安全
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
public interface IRabbitMqMessageLisenter {

    /**
     * 接口方法实现消息的处理
     * @param delivery
     */
    void processMessage(Delivery delivery) throws Exception;

}
