/**
 * @FileName: ISmsSender.java
 * @Package: com.asrua.framework.sms
 * @author sence
 * @created 10/10/2015 3:01 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asrua.framework.sms.send;

import com.asura.framework.base.entity.DataTransferObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.concurrent.Future;

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
public interface ISmsSender {

    /**
     * 发送短信 同步
     *
     * @param smsMessage
     * @return
     */
    DataTransferObject send(SmsMessage smsMessage) throws IOException;

    /**
     * 异步发送短信
     *
     * @param smsMessage
     * @return
     */
    Future<DataTransferObject> asyncSend(SmsMessage smsMessage) throws URISyntaxException, UnsupportedEncodingException;

}
