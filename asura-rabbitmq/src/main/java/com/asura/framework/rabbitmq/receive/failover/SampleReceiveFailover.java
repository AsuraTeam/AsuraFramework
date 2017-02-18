/**
 * @FileName: ErrorConsumerFailover.java
 * @Package: com.asura.framework.rabbitmq.receive.failover
 * @author liusq23
 * @created 7/21/2016 5:05 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbitmq.receive.failover;

import com.asura.framework.rabbitmq.receive.IRabbitMqReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>
 * 出现异常错误，错误处理，当出现异常错误(ShutDown,ChannelClose)
 * 线程会中断
 * 则认为需要和服务器重新建立链接
 * </p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liusq23
 * @version 1.0
 * @since 1.0
 */
public class SampleReceiveFailover implements IReceiveFailover {

    private Logger LOGGER = LoggerFactory.getLogger(SampleReceiveFailover.class);

    private IRabbitMqReceiver iRabbitMqReceiver;

    private AtomicBoolean isInFail = new AtomicBoolean(false);

    public IRabbitMqReceiver getiRabbitMqReceiver() {
        return iRabbitMqReceiver;
    }

    public void setiRabbitMqReceiver(IRabbitMqReceiver iRabbitMqReceiver) {
        this.iRabbitMqReceiver = iRabbitMqReceiver;
    }

    public SampleReceiveFailover() {
    }


    public SampleReceiveFailover(IRabbitMqReceiver iRabbitMqReceiver) {
        this.iRabbitMqReceiver = iRabbitMqReceiver;
    }

    @Override
    public void doFailover() {
        LOGGER.info("[rabbitmq consume] start to read flag...");
        boolean isInHandleFailFlag = isInFail.getAndSet(true);
        if(isInHandleFailFlag){
            LOGGER.info("[rabbitmq consume] there is a thread has failed...");
            return;
        }
        LOGGER.info("[rabbitmq consume] start to destory resource...");
        try {
            iRabbitMqReceiver.destroyResource();
        } catch (Exception e) {
            LOGGER.warn("[rabbitmq consume] destory resource error:", e);
        }
        LOGGER.info("[rabbitmq consume] start to wait...");
        try {
            Thread.sleep(1 * 60 * 1000);
        } catch (InterruptedException e) {
            LOGGER.error("[rabbitmq consume] sleep interrupt:", e);
        }
        LOGGER.info("[rabbitmq consume] start to reconnection...");
        try {
            isInFail.set(false);
            iRabbitMqReceiver.receiveMessage();
        } catch (Exception e) {
            LOGGER.error("[rabbitmq consume] cannot start receiver:", e);
        }
        LOGGER.info("[rabbitmq consume] start success...");
    }
}
