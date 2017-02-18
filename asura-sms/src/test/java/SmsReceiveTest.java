/**
 * @FileName: SmsReceiveTest.java
 * @Package: PACKAGE_NAME
 * @author sence
 * @created 12/3/2015 1:43 PM
 * <p/>
 * Copyright 2017 Asura
 */

import com.asrua.framework.sms.SmsReceiveFactory;
import com.asrua.framework.sms.conf.TokenSmsSenderConfig;
import com.asrua.framework.sms.send.ISmsReceiver;
import com.asura.framework.base.entity.DataTransferObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

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
public class SmsReceiveTest {


    @Test
    public void testReceive() throws IOException {
        TokenSmsSenderConfig config = new TokenSmsSenderConfig();
        config.setToken("gWl03z5bTayKQmibPEb5cg");
        config.setCharset("UTF-8");
        config.setProtocol("http");
        config.setHost("message.t.com");
        config.setPath("/api/sms/uplink");
        SmsReceiveFactory smsReceiveFactory = new SmsReceiveFactory();
        ISmsReceiver receiver = smsReceiveFactory.buildReceiver(config);

        DataTransferObject dto = receiver.receive();
        Assert.assertNotNull(dto);
    }


}
