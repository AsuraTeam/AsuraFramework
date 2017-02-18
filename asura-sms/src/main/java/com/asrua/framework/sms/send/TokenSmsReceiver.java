/**
 * @FileName: TokenSmsReceiver.java
 * @Package: com.asrua.framework.sms.send
 * @author sence
 * @created 12/3/2015 10:47 AM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asrua.framework.sms.send;

import com.asrua.framework.sms.conf.TokenSmsSenderConfig;
import com.asura.framework.base.util.JsonEntityTransform;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * <p></p>
 * <p>
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
public class TokenSmsReceiver extends AbstractHttpSmsReceiver {

    /**
     * 使用父亲级的构造
     *
     * @param tokenSmsSenderConfig
     */
    public TokenSmsReceiver(TokenSmsSenderConfig tokenSmsSenderConfig) {
        super(tokenSmsSenderConfig);
    }

    @Override
    protected HttpPost buildPostParam() throws UnsupportedEncodingException, URISyntaxException {
        URI uri = super.buildURIByConfig();
        HttpPost httpPost = new HttpPost(uri);
        TokenSmsSenderConfig config = (TokenSmsSenderConfig) getConfig();
        HashMap<String, String> map = new HashMap<>();
        map.put("token", config.getToken());
        StringEntity entity = new StringEntity(JsonEntityTransform.Object2Json(map), config.getCharset());
        httpPost.setEntity(entity);
        return httpPost;
    }
}
