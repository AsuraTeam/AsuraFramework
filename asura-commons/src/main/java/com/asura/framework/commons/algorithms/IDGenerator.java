/**
 * @FileName: IDGenerator.java
 * @Package: com.asura.framework.commons.algorithms
 * @author liusq23
 * @created 2016/12/1 下午8:57
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
public interface IDGenerator {

    /**
     * 设置id前缀
     *
     * @param identity
     * @return
     */
    void setIdentity(String identity);

    /**
     * 设置机器的id
     *
     * @param workerId
     * @return
     */
    void setWorkerId(int workerId);

    /**
     * 返回id
     *
     * @return
     */
    String nextId();

}
