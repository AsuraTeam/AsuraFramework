/**
 * @FileName: HttpUtil.java
 * @Package: com.asrua.framework.sms.send
 * @author sence
 * @created 10/11/2015 1:58 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asrua.framework.sms.send;

import com.asrua.framework.sms.log.LogInfoBean;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
public class HttpUtil {

    private HttpUtil() {

    }

    public static String handlerRespone(CloseableHttpResponse response, LogInfoBean logInfoBean) throws IOException {
        int status = response.getStatusLine().getStatusCode();
        String resultStr = null;
        if (status == 200) {
            resultStr = EntityUtils.toString(response.getEntity());
        }
        logInfoBean.setReturnCode(status + "");
        logInfoBean.setReturnInfo(resultStr);
        return resultStr;
    }
}
