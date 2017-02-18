/*
 * Copyright (c) 2017 Asura
 */
package com.asura.framework.commons.json;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
 * @date 2016/11/7 14:51
 * @since 1.0
 */
public class TArticle {
    private String title;
    private String context;
    private TUser user;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @JsonBackReference
    public TUser getUser() {
        return user;
    }

    public void setUser(TUser user) {
        this.user = user;
    }
}
