/**
 * @FileName: RoutingKey.java
 * @Package: com.asura.framework.rabbitmq.entity
 * @author sence
 * @created 3/12/2016 11:14 AM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbitmq.entity;

import com.asura.framework.commons.util.RegExpUtil;
import com.asura.framework.rabbitmq.exception.AsuraRabbitMqException;

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
public class RoutingKey extends NameKey {

    public RoutingKey() {

    }

    public RoutingKey(String system, String module, String function) {
        super(system, module, function);
    }

    /**
     * 获取到routingKey
     */
    public String getKey() throws AsuraRabbitMqException {
        if (this.getSystem() == null || "".equals(this.getSystem())) {
            throw new AsuraRabbitMqException("RoutingKey:system is null");
        }

        if (this.getModule() == null || "".equals(this.getModule())) {
            throw new AsuraRabbitMqException("RoutingKey:module is null");
        }

        if (this.getFunction() == null || "".equals(this.getFunction())) {
            throw new AsuraRabbitMqException("RoutingKey:function is null");
        }

        if (!RegExpUtil.isNameOfLettersOrUnderline(this.getSystem())) {
            throw new AsuraRabbitMqException("RoutingKey:system name must match [A-Za-z0-9_]+");
        }

        if (!RegExpUtil.isNameOfLettersOrUnderline(this.getModule())) {
            throw new AsuraRabbitMqException("RoutingKey:module name must match [A-Za-z0-9_]+");
        }

        if (!RegExpUtil.isNameOfLettersOrUnderline(this.getFunction())) {
            throw new AsuraRabbitMqException("RoutingKey:function name must match [A-Za-z0-9_]+");
        }
        return getSystem() + "." + getModule() + "." + getFunction();
    }

}
