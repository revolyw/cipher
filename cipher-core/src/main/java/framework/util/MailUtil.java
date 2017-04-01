package framework.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 邮件发送工具
 * Created by Willow on 4/1/17.
 */
public class MailUtil {
    public static void main(String[] args) throws MessagingException {
        Properties props = new Properties();
        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.yeah.net");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        // 设置环境信息
        Session session = Session.getInstance(props);

        // 创建邮件对象
        Message msg = new MimeMessage(session);
        msg.setSubject("JavaMail测试");
        // 设置邮件内容
        msg.setText("这是一封由JavaMail发送的邮件！");
        // 设置发件人
        msg.setFrom(new InternetAddress("send_mail_address"));

        Transport transport = session.getTransport();
        // 连接邮件服务器
        transport.connect("account", "password");
        // 发送邮件
        transport.sendMessage(msg, new Address[] {new InternetAddress("receive_mail_address")});
        // 关闭连接
        transport.close();
    }
}
