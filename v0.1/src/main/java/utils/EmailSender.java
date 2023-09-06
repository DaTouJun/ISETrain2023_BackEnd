package utils;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


public class EmailSender {
    public static void send(String content, String emailAddress) {
        // 配置SMTP服务器和认证信息
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.163.com"); // 设置SMTP服务器地址
        properties.put("mail.smtp.port", "25"); // 设置SMTP服务器端口
        properties.put("mail.smtp.auth", "true"); // 启用SMTP服务器认证

        // 创建Session对象
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("Da_Tou_Jun@163.com", "FTEIULWHCGTXBSNE"); // 设置发件人邮箱和密码
            }
        });

        try {
            // 创建MimeMessage对象
            Message message = new MimeMessage(session);

            // 设置发件人
            message.setFrom(new InternetAddress("Da_Tou_Jun@163.com"));

            // 设置收件人
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress));

            // 设置邮件主题
            message.setSubject("这里是商品管理系统后端 您的验证码");

            // 设置邮件内容
            message.setText(content);

            // 发送邮件
            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
