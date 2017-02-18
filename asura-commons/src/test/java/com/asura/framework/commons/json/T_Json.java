/*
 * Copyright (c) 2017 Asura
 */
package com.asura.framework.commons.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunzhenlei
 * @version 1.0
 * @date 2016/11/7 14:47
 * @since 1.0
 */

public class T_Json {

    private static final Logger LOGGER = LoggerFactory.getLogger(T_Json.class);

    private TUser user;

    private TArticle article;

    @Before
    public void setUp() throws Exception {
        user = new TUser();
        user.setName("chris");
        user.setCreateDate(new Date());
        article = new TArticle();
        article.setTitle("title");
        article.setUser(user);
        Set<TArticle> articles = Sets.newHashSet(article);
        user.setArticles(articles);
    }

    @Test
    public void extend() throws Exception {
        user = new TUser();
        user.setName("chris");
        user.setCreateDate(new Date());
        article = new TBlog();
        article.setTitle("title");
        article.setUser(user);
        TBlog blog = (TBlog) article;
        blog.setZang(1);
        // System.out.print(Json.toJsonString(article));

    }

    @org.junit.Test
    public void testParseObject() throws Exception {
        String json = "{\"name\":\"chris\",\"createDate\":\"2016-11-07 15:21:25.025\",\"articles\":[{\"title\":\"title\",\"context\":null}]}";
        TUser u = Json.parseObject(json, TUser.class);
        LOGGER.info(u.getName());
    }

    @org.junit.Test
    public void testParseJson() throws Exception {
        String json = Json.toJsonString(user);
        LOGGER.info(json);

        TUser u1 = new TUser();
        u1.setName("chris1");
        u1.setCreateDate(new Date());
        TArticle article1 = new TArticle();
        article1.setTitle("title1");
        article1.setUser(u1);
        Set<TArticle> articles = Sets.newHashSet(article1);
        u1.setArticles(articles);

        TUser u2 = new TUser();
        u2.setName("chris2");
        u2.setCreateDate(new Date());
        TArticle article2 = new TArticle();
        article2.setTitle("title2");
        article2.setUser(u1);
        Set<TArticle> articles2 = Sets.newHashSet(article2);
        u2.setArticles(articles2);

        List<TUser> list = new ArrayList<>();
        list.add(u1);
        list.add(u2);
        json = Json.toJsonString(list);
        LOGGER.info(json);
    }

    @org.junit.Test
    public void testParseObjectList() throws Exception {
        String json = "[{\"name\":\"chris1\",\"createDate\":\"2016-11-07 15:25:34.034\",\"articles\":[{\"title\":\"title1\",\"context\":null}]},{\"name\":\"chris2\",\"createDate\":\"2016-11-07 15:25:34.034\",\"articles\":[{\"title\":\"title2\",\"context\":null}]}]";
        List<TUser> list = Json.parseObjectList(json, TUser.class);
        LOGGER.info(list.size() + "");
    }


    @org.junit.Test
    public void testGetPathObject() throws Exception {
        String json = "{\"name\":\"chris\",\"createDate\":\"2016-11-07 15:21:25.025\",\"articles\":[{\"title\":\"title\",\"context\":null}]}";
        List articles = Json.getPathObject(json, List.class, "articles");
        assertNotNull(articles);
    }

    @org.junit.Test
    public void testGetPathArray() throws Exception {
        String json = "{\"name\":\"chris\",\"createDate\":\"2016-11-07 15:21:25.025\",\"articles\":[{\"title\":\"title\",\"context\":null}]}";
        List articles = Json.getPathArray(json, TArticle.class, "articles");
        assertNotNull(articles);
    }

    @org.junit.Test
    public void t_getString_01() throws Exception {
        String json = "{\"name\":\"chris\",\"createDate\":\"2016-11-07 15:21:25.025\",\"articles\":[{\"title\":\"title\",\"context\":null}]}";
        String articles = Json.getString(json, "articles");
        assertThat(articles, containsString("[{\"title\":\"title\",\"context\":null}]"));
        JsonNode jsonNode = Json.parseJsonNode(json, "ss");
        assertThat(jsonNode, equalTo(null));
        String createDate = Json.getString(json, "createDate");
        assertThat(createDate, containsString(".025"));
    }

    @Test
    public void t_parse_02(){
        String json = "{\"data\":\"{\\\"messageCode\\\":\\\"move31464682823872\\\",\\\"backQueueName\\\":\\\"gaea_supplier_supplierFeedBackQueue\\\",\\\"messageContent\\\":\\\"{\\\\\\\"operatorId\\\\\\\":\\\\\\\"15110277264\\\\\\\",\\\\\\\"userOrderCode\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"operatorName\\\\\\\":\\\\\\\"北京厢货搬家供应商\\\\\\\",\\\\\\\"sendTime\\\\\\\":1482304829315,\\\\\\\"supplierName\\\\\\\":\\\\\\\"北京厢货搬家供应商\\\\\\\",\\\\\\\"employeeName\\\\\\\":\\\\\\\"test\\\\\\\",\\\\\\\"employeePhone\\\\\\\":null,\\\\\\\"statusOperates\\\\\\\":[{\\\\\\\"key\\\\\\\":\\\\\\\"orderStatus\\\\\\\",\\\\\\\"originalStatus\\\\\\\":10,\\\\\\\"aimStatus\\\\\\\":15}],\\\\\\\"nodeOperates\\\\\\\":[{\\\\\\\"key\\\\\\\":\\\\\\\"fistTaskingTime\\\\\\\",\\\\\\\"aimTime\\\\\\\":1482304829315}]}\\\"}\",\"sendTimeStr\":\"Wed Dec 21 15:20:29 CST 2016\",\"type\":\"d.EX_gaea_supplier_dispatchOrderToUpExchange\"}";
        String s = Json.getString(json,"data");
        Assert.assertNotNull(s);
    }
}
