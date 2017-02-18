/**
 * @FileName: PageRequest.java
 * @Package: com.asura.framework.base.dto
 * @author liusq23
 * @created 2016/12/21 下午4:07
 * <p>
 * Copyright 2017 Asura
 */
package com.asura.framework.base.dto;

/**
 * <p>
 * 分页请求
 * </p>
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
public abstract class PageRequest extends BaseRequest {
    /**
     * 页码
     */
    private int page;
    /**
     * 页大小
     */
    private int pageSize;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
