/**
 * @FileName: T_AsuraCommonsHttpclient.java
 * @Package: com.asura.framework.commons.net
 * @author liusq23
 * @created 2016/11/30 下午3:33
 * <p>
 * Copyright 2017 Asura
 */
package com.asura.framework.commons.net;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
public class T_AsuraCommonsHttpclient {

    /**
     *
     */
    @Test
    public void t_doGet_01() throws IOException {
        String url = "http://www.baidu.com";
        String s = AsuraCommonsHttpclient.getInstance().doGet(url);
        Assert.assertThat(s, CoreMatchers.containsString("百度一下"));
    }


    /**
     *
     */
    @Test
    public void t_doGet_02() throws IOException {
        String url = "http://10.30.27.16:8083/exdemo?name=sence&command=CODE";
        String s = AsuraCommonsHttpclient.getInstance().doGet(url);
        Assert.assertThat(s, CoreMatchers.containsString("出现了自定义code业务异常"));
    }

    /**
     *
     */
    @Test
    public void t_doPost_01() throws IOException {
        String url = "http://10.30.27.16:8083/exdemo";
        String s = AsuraCommonsHttpclient.getInstance().doPostForm(url, new HashMap<String, String>());
        Assert.assertThat(s, CoreMatchers.containsString("名称不能为空"));
    }

    /**
     *
     */
    @Test
    public void t_doPost_02() throws IOException {
        String url = "http://10.30.27.16:8083/exdemo";
        Map<String, String> map = new HashMap<>();
        map.put("name", "sence");
        map.put("command", "CODE");
        String s = AsuraCommonsHttpclient.getInstance().doPostForm(url, map);
        Assert.assertThat(s, CoreMatchers.containsString("出现了自定义code业务异常"));
    }


}
