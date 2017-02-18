package com.asura.framework.elasticsearch;

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
 * @date 2016/8/30 22:48
 * @since 1.0
 */
public class EsResponse {
    public final static String SUCCESS = "0";
    public final static String FAILED = "1";
    /**
     * 0:成功,1:失败，其他：具体沟通
     */
    private String status;
    /**
     * 错误反馈信息
     */
    private String message;
    /**
     * 响应数据
     */
    private Object data;

    public EsResponse(String message) {
        this.status = FAILED;
        this.message = message;

    }

    public EsResponse(String status, String message) {
        this.status = status;
        this.message = message;

    }

    public EsResponse(Object data) {
        this.status = SUCCESS;
        this.data = data;

    }

    public EsResponse() {

    }

    /**
     * @param obj
     * @return
     */
    public static EsResponse responseOK(Object obj) {
        EsResponse EsResponse = new EsResponse();
        EsResponse.setStatus(SUCCESS);
        EsResponse.setData(obj);
        return EsResponse;
    }

    /**
     * @param str
     * @return
     */
    public static EsResponse responseFail(String str) {
        EsResponse EsResponse = new EsResponse();
        EsResponse.setStatus(FAILED);
        EsResponse.setMessage(str);
        return EsResponse;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
