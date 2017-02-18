/**
 * @FileName: TBlog.java
 * @Package: com.asura.framework.commons.json
 * @author liusq23
 * @created 2016/12/1 下午2:23
 * <p>
 * Copyright 2017 Asura
 */
package com.asura.framework.commons.json;

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
public class TBlog extends TArticle {

    private Integer zang;

    public Integer getZang() {
        return zang;
    }

    public void setZang(Integer zang) {
        this.zang = zang;
    }
}
