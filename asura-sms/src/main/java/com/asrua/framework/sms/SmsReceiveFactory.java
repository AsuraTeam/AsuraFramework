/**
 * @FileName: SmsReceiveFactory.java
 * @Package: com.asrua.framework.sms
 * @author sence
 * @created 12/3/2015 1:41 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asrua.framework.sms;

import com.asrua.framework.sms.conf.AbstractSmsSenderConfig;
import com.asrua.framework.sms.conf.TokenSmsSenderConfig;
import com.asrua.framework.sms.exception.NotSupportSmsTypeException;
import com.asrua.framework.sms.send.ISmsReceiver;
import com.asrua.framework.sms.send.TokenSmsReceiver;
import com.asura.framework.base.util.Check;

import java.util.concurrent.ConcurrentHashMap;

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
public class SmsReceiveFactory {

    /**
     * 缓存已经创建的Sender
     */
    private ConcurrentHashMap<String, ISmsReceiver> concurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 根据不同的配置创建不同的发送者
     *
     * @return
     */
    public ISmsReceiver buildReceiver(AbstractSmsSenderConfig config) {
        if (config instanceof TokenSmsSenderConfig) {
            TokenSmsSenderConfig tokenSmsSenderConfig = (TokenSmsSenderConfig) config;
            return buildTokenConfig(tokenSmsSenderConfig);
        } else {
            throw new NotSupportSmsTypeException("not support send sms type");
        }
    }

    /**
     * 创建token短信发送者
     *
     * @param tokenSmsSenderConfig
     * @return
     */
    private ISmsReceiver buildTokenConfig(TokenSmsSenderConfig tokenSmsSenderConfig) {
        ISmsReceiver smsReceiver = concurrentHashMap.get(tokenSmsSenderConfig.getSendUrl());
        if (Check.NuNObj(smsReceiver)) {
            smsReceiver = new TokenSmsReceiver(tokenSmsSenderConfig);
            concurrentHashMap.put(tokenSmsSenderConfig.getSendUrl(), smsReceiver);
        }
        return smsReceiver;
    }

}
