package com.asura.framework.elasticsearch.po;

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
 * @date 2016/8/31 14:12
 * @since 1.0
 */
public class DocPo {
    /**
     * esId，类似主键
     */
    private String id;
    /**
     * 文档对象
     */
    private Object  obj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
