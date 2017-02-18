/*
 * Copyright (c) 2017 Asura
 */
package com.asura.framework.commons.json;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.Sets;
import java.util.Date;
import java.util.Set;

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
 * @date 2016/11/7 14:50
 * @since 1.0
 */
public class TUser {

    private String name;
    private Date createDate;
    private Set<TArticle> articles = Sets.newHashSet();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @JsonManagedReference
    public Set<TArticle> getArticles() {
        return articles;
    }

    public void setArticles(Set<TArticle> articles) {
        this.articles = articles;
    }
}
