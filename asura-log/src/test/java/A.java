/**
 * @FileName: A.java
 * @Package: PACKAGE_NAME
 * @author liusq23
 * @created 2016/11/14 下午4:30
 * <p>
 * Copyright 2017 Asura
 */

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class A {
    private static Logger logger = LoggerFactory.getLogger(A.class);

    public static void main(String[] args) {
        try {
            Integer.parseInt("ss");
        } catch (Exception e) {
            System.out.print(JSON.toJSONString(e));

        }
    }
}
