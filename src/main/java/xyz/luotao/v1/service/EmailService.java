package xyz.luotao.v1.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // 发送激活邮件
    public void sendActivationMail(String to, String activateUrl) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, StandardCharsets.UTF_8.name());
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("账号激活");
            String html = "<p>您好，感谢注册。</p>"
                    + "<p>请点击下方链接完成激活（24小时内有效）：</p>"
                    + "<p><a href=\"" + activateUrl + "\">" + activateUrl + "</a></p>";
            helper.setText(html, true);
            mailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException("激活邮件发送失败", e);
        }
    }

    //发送激活成功邮件
    public void sendActivationSuccessMail(String to) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, StandardCharsets.UTF_8.name());
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("账号激活成功");
            String html = "<p>您好，您的账号已成功激活！</p>"
                    + "<p>现在您可以使用您的账号登录并享受我们的服务。</p>";
            helper.setText(html, true);
            mailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException("激活成功邮件发送失败", e);
        }
    }
}
