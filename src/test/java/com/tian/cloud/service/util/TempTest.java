package com.tian.cloud.service.util;

import org.junit.Before;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.File;
import java.security.GeneralSecurityException;

import static com.tian.cloud.service.util.OhMyEmail.SMTP_QQ;

/**
 * @author tianguang
 * 2018/9/5 下午2:18
 **/
public class TempTest {

    @Test
    public void testSendText() throws MessagingException {
        long start = System.currentTimeMillis();
        OhMyEmail.subject("这是一封测试TEXT邮件")
                .from("田广")
                .to("308929467@qq.com")
                .text("信件内容")
                .send();

        System.out.println(System.currentTimeMillis() - start);
        OhMyEmail.subject("这是一封测试TEXT邮件")
                .from("田广")
                .to("308929467@qq.com")
                .text("信件内容")
                .attach(new File(""))
                .send();
        System.out.println(System.currentTimeMillis() - start);

    }
}
