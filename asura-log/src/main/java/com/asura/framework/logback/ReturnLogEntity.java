/**
 * @FileName:
 * @Package: com.asura.services.log
 * @author sence
 * @created 11/3/2014 6:42 PM
 * <p>
 * Copyright 2011-2015 Asura
 */
package com.asura.framework.logback;

import com.asura.framework.commons.json.Json;

/**
 *
 * <p>系统日志返回数据日志实体</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @since 1.0
 * @version 1.0
 */
public class ReturnLogEntity extends DataLogEntity {

    /**
     *
     */
    private static final long serialVersionUID = 745354180459889134L;
    //返回值记录日志
    private String returnVal;

    public ReturnLogEntity(DataLogEntity dle) {
        super(dle.getClassName(), dle.getMethodName(), dle.getParams());
    }

    public ReturnLogEntity() {

    }

    public String getReturnVal() {
        return returnVal;
    }

    public void setReturnVal(Object returnVal) {
        this.returnVal = Json.toJsonString(returnVal);
    }

}
