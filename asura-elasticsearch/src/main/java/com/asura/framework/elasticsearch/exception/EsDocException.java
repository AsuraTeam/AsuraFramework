package com.asura.framework.elasticsearch.exception;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jiangn18
 * @version 1.0
 * @date 2016/8/31 21:21
 * @since 1.0
 */
public class EsDocException extends Exception{
    public EsDocException () {

    }

    public EsDocException (String message) {
        super(message);
    }
}
