package com.semicolon.itaxi.services;

import com.semicolon.itaxi.dto.requests.EmailNotificationRequest;
import com.semicolon.itaxi.dto.requests.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailNotificationServiceImpl implements EmailNotificationService{

    private final JavaMailSender javaMailSender;
    @Override
    public String sendHtmlMail(EmailNotificationRequest emailNotification) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom("no-reply@email.iTaxi.com.ng");
            mimeMessageHelper.setTo(emailNotification.getUserEmail());
            mimeMessageHelper.setText(emailNotification.getMailContent(), true);
            javaMailSender.send(mimeMessage);
            return String.format("email sent to %s successfully", emailNotification.getUserEmail());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return String.format("email not sent to %s", emailNotification.getUserEmail());
    }

    @Override
    public String send(NotificationRequest notificationRequest) {
        return null;
    }
}
