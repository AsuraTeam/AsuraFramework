/**
 * @FileName: SmsSenderFactory.java
 * @Package: com.asrua.framework.sms
 * @author sence
 * @created 10/10/2015 3:39 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asrua.framework.sms;

import com.asrua.framework.sms.conf.AbstractSmsSenderConfig;
import com.asrua.framework.sms.conf.GenericSmsSenderConfig;
import com.asrua.framework.sms.conf.TokenSmsSenderConfig;
import com.asrua.framework.sms.exception.NotSupportSmsTypeException;
import com.asrua.framework.sms.send.GenericSmsSender;
import com.asrua.framework.sms.send.ISmsSender;
import com.asrua.framework.sms.send.TokenSmsSender;
import com.asura.framework.base.util.Check;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>短信创建Sender Factory</p>
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
public class SmsSenderFactory {
    /**
     * 缓存已经创建的Sender
     */
    private ConcurrentHashMap<String, ISmsSender> concurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 根据不同的配置创建不同的发送者
     *
     * @return
     */
    public ISmsSender buildSender(AbstractSmsSenderConfig config) {
        if (config instanceof GenericSmsSenderConfig) {
            GenericSmsSenderConfig genericSmsSenderConfig = (GenericSmsSenderConfig) config;
            return buildGenericSender(genericSmsSenderConfig);
        } else if (config instanceof TokenSmsSenderConfig) {
            TokenSmsSenderConfig tokenSmsSenderConfig = (TokenSmsSenderConfig) config;
            return buildTokenSender(tokenSmsSenderConfig);
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
    private ISmsSender buildTokenSender(TokenSmsSenderConfig tokenSmsSenderConfig) {
        ISmsSender smsSender = concurrentHashMap.get(tokenSmsSenderConfig.getSendUrl());
        if (Check.NuNObj(smsSender)) {
            smsSender = new TokenSmsSender(tokenSmsSenderConfig);
            concurrentHashMap.put(tokenSmsSenderConfig.getSendUrl(), smsSender);
        }
        return smsSender;
    }

    /**
     * 创建通用的短信发送者
     *
     * @param genericSmsSenderConfig
     * @return
     */
    private ISmsSender buildGenericSender(GenericSmsSenderConfig genericSmsSenderConfig) {
        ISmsSender smsSender = concurrentHashMap.get(genericSmsSenderConfig.getSendUrl());
        if (Check.NuNObj(smsSender)) {
            smsSender = new GenericSmsSender(genericSmsSenderConfig);
            concurrentHashMap.put(genericSmsSenderConfig.getSendUrl(), smsSender);
        }
        return smsSender;
    }

}
