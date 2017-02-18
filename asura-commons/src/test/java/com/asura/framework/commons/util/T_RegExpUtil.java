/**
 * @FileName: T_RegExpUtil.java
 * @Package: com.asura.framework.commons
 * @author liusq23
 * @created 2016/12/8 上午9:45
 * <p>
 * Copyright 2017 Asura
 */
package com.asura.framework.commons.util;

import org.junit.Assert;
import org.junit.Test;

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
public class T_RegExpUtil {

    @Test
    public void t_isNameOfLettersOrUnderline_01() {
        String str = "adas1239130132_";
        boolean isTrue = RegExpUtil.isNameOfLettersOrUnderline(str);
        Assert.assertTrue(isTrue);
    }

    @Test
    public void t_isNameOfLettersOrUnderline_02() {
        String str = "{adas123913X132_}";
        boolean isTrue = RegExpUtil.isNameOfLettersOrUnderline(str);
        Assert.assertTrue(!isTrue);
    }


    @Test
    public void t_isNameOfLettersOrUnderline_03() {
        String str = "";
        boolean isTrue = RegExpUtil.isNameOfLettersOrUnderline(str);
        Assert.assertTrue(!isTrue);
    }
}
