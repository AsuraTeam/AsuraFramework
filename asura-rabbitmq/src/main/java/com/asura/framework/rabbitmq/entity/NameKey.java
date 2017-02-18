/**
 * @FileName: NameKey.java
 * @Package: com.asura.framework.rabbitmq.entity
 * @author sence
 * @created 3/12/2016 10:53 AM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.rabbitmq.entity;

/**
 * <p>作为队列的名称或者routingKey BingKey来</p>
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
public abstract class NameKey {
    /**
     * 系统
     */
    private String system;
    /**
     * 模块
     */
    private String module;
    /**
     * 功能
     */
    private String function;

    public NameKey() {

    }

    public NameKey(String system, String module, String function) {
        this.system = system;
        this.module = module;
        this.function = function;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
