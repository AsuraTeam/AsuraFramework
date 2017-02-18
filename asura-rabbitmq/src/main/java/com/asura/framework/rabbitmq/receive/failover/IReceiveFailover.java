/**
 * @FileName: IconsumeFailover.java
 * @Package: com.asura.framework.rabbitmq.receive
 * @author liusq23
 * @created 7/21/2016 4:41 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbitmq.receive.failover;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liusq23
 * @since 1.0
 * @version 1.0
 */
public interface IReceiveFailover {
    /**
     *
     * 为保证幂等性，不重复执行
     *
     */
    void doFailover();

}
