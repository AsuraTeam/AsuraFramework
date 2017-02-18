package com.asura.framework.conf.common;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunzhenlei
 * @version 1.0
 * @date 2016/8/17 16:31
 * @since 1.0
 */
public class DataMarshallingException extends RuntimeException {

    private static final long serialVersionUID = -8059710342787509974L;

    public DataMarshallingException() {
        super();
    }

    public DataMarshallingException(Throwable cause) {
        super(cause);
    }

    public DataMarshallingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataMarshallingException(String message) {
        super(message);
    }
}
