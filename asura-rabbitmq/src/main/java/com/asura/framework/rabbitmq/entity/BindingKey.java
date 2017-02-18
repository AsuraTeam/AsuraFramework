/**
 * @FileName: BindKey.java
 * @Package: com.asura.framework.rabbitmq.entity
 * @author sence
 * @created 3/12/2016 11:15 AM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbitmq.entity;

import com.asura.framework.commons.util.Check;
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
public class BindingKey extends NameKey {

    public BindingKey() {

    }

    public BindingKey(String system, String module, String function) {
        super(system, module, function);
    }


    /**
     * 获取到routingKey
     */
    public String getKey() throws AsuraRabbitMqException {
        if (Check.isNullOrEmpty(this.getSystem())) {
            this.setSystem("*");
        } else {
            if (!RegExpUtil.isNameOfLettersOrUnderline(this.getSystem())) {
                throw new AsuraRabbitMqException("BindingKey:system name must match [A-Za-z0-9_]+");
            }
        }
        if (Check.isNullOrEmpty(this.getModule())) {
            this.setModule("*");
        } else {
            if (!RegExpUtil.isNameOfLettersOrUnderline(this.getModule())) {
                throw new AsuraRabbitMqException("BindingKey:module name must match [A-Za-z0-9_]+");
            }
        }
        if (Check.isNullOrEmpty(this.getFunction())) {
            this.setFunction("*");
        } else {
            if (!RegExpUtil.isNameOfLettersOrUnderline(this.getFunction())) {
                throw new AsuraRabbitMqException("BindingKey:function name must match [A-Za-z0-9_]+");
            }
        }
        return getSystem() + "." + getModule() + "." + getFunction();
    }

}
