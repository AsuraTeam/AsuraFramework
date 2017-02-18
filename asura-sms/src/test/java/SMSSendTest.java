/**
 * @FileName: SMS.java
 * @Package: PACKAGE_NAME
 * @author sence
 * @created 10/11/2015 3:03 PM
 * <p/>
 * Copyright 2017 Asura
 */

import com.asrua.framework.sms.SmsSenderFactory;
import com.asrua.framework.sms.conf.GenericSmsSenderConfig;
import com.asrua.framework.sms.conf.TokenSmsSenderConfig;
import com.asrua.framework.sms.send.ISmsSender;
import com.asrua.framework.sms.send.SmsMessage;
import com.asura.framework.base.entity.DataTransferObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
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
public class SMSSendTest {

    @Test
    public void testSend() throws IOException {
        GenericSmsSenderConfig config = new GenericSmsSenderConfig();
        config.setAccountName("liushengqi");
        config.setPassword("asdasd");
        config.setPort(80);
        config.setCharset("UTF-8");
        config.setProtocol("http");
        config.setHost("asura.host.com");
        config.setPath("/zrk/send/sms");
        SmsSenderFactory smsSenderFactory = new SmsSenderFactory();
        ISmsSender smsSender = smsSenderFactory.buildSender(config);
        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setToPhone("18801084234");
        smsMessage.setContent("hello world");
        DataTransferObject dto = smsSender.send(smsMessage);
        Assert.assertNotNull(dto);
    }


    @Test
    public void testAsyncSend() throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        GenericSmsSenderConfig config = new GenericSmsSenderConfig();
        config.setAccountName("liushengqi");
        config.setPassword("asdasd");
        config.setPort(80);
        config.setCharset("UTF-8");
        config.setProtocol("http");
        config.setHost("asura.host.com");
        config.setPath("/zrk/send/sms");
        SmsSenderFactory smsSenderFactory = new SmsSenderFactory();
        ISmsSender smsSender = smsSenderFactory.buildSender(config);
        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setToPhone("18801084234");
        smsMessage.setContent("hello world");
        Future<DataTransferObject> future = smsSender.asyncSend(smsMessage);
        DataTransferObject dataTransferObject = future.get();
    }

    @Test
    public void test_AsyncSend() throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        TokenSmsSenderConfig config = new TokenSmsSenderConfig();
        config.setToken("gXT9Wi5zRG6acpU38_BeIw");
        config.setCharset("UTF-8");
        config.setProtocol("http");
        config.setHost("asura.host.com");
        config.setPath("/api/sms/send");
        SmsSenderFactory smsSenderFactory = new SmsSenderFactory();
        ISmsSender smsSender = smsSenderFactory.buildSender(config);
        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setToPhone("18801072468");
        smsMessage.setContent("hello world");
        smsSender.asyncSend(smsMessage);
        Thread.sleep(5000);
    }
}
