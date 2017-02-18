/**
 * @FileName: AbstractIDGenerator.java
 * @Package: com.asura.framework.commons.algorithms
 * @author liusq23
 * @created 2016/12/1 下午9:03
 * <p>
 * Copyright 2017 Asura
 */
package com.asura.framework.commons.algorithms;

/**
 * <p></p>
 * <p>
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
public abstract class AbstractIDGenerator implements IDGenerator {
    /**
     *
     */
    private String identity;
    /**
     *
     */
    private int workerId;

    protected AbstractIDGenerator() {

    }

    protected AbstractIDGenerator(String identity, int workerId) {
        this.workerId = workerId;
        this.identity = identity;
    }

    @Override
    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Override
    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getIdentity() {
        return identity;
    }

    public int getWorkerId() {
        return workerId;
    }

    @Override
    public abstract String nextId();
}
