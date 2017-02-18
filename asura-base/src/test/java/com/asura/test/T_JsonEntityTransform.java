/**
 * @FileName: T_JsonEntityTransform.java
 * @Package: com.asura.test
 * @author liusq23
 * @created 2016/11/28 上午9:48
 * <p>
 * Copyright 2017 Asura
 */
package com.asura.test;

import com.asura.framework.base.util.JsonEntityTransform;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

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
public class T_JsonEntityTransform {

    private User user;

    @org.junit.Before
    public void setUp() throws Exception {
        user = new User();
        user.setName("chris");
        user.setCreateDate(new Date());
    }

    @Test
    public void t_json2Entity_01() {
        String s = JsonEntityTransform.Object2Json(user);
        Assert.assertThat(s, CoreMatchers.containsString("chris"));
    }

    @Test
    public void t_json2Object_02() {
        String s = "{\"name\":\"chris\"}";
        User user = JsonEntityTransform.json2Object(s, User.class);
        Assert.assertEquals(user.getName(), "chris");
    }


}
