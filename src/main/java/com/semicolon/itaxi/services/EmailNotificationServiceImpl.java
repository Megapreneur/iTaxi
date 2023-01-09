package com.semicolon.itaxi.services;

import com.semicolon.itaxi.dto.requests.*;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailNotificationServiceImpl implements EmailNotificationService{
    private String baseUrl;
    private final JavaMailSender javaMailSender;
    @Override
    public String sendHtmlMail(EmailNotificationRequest emailNotification) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom("no-reply@email.iTaxi.com.ng");
            mimeMessageHelper.setTo(emailNotification.getUserEmail());
            mimeMessageHelper.setText(
                    ""
            );
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

    @Override
    public void sendWelcomeMessageToCustomer(RegisterUserRequest userDto, String token) {
        String link = baseUrl + "/v1/user/verify?token=" +token;
        final SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(userDto.getEmail());
        mailMessage.setSubject("Welcome to iTaxi " + userDto.getName());
        mailMessage.setFrom("info@iTaxi.africa");
        mailMessage.setText(
                "For your seamless movement across any where in the country, your safety is our priority. We hope you enjoy every trip with us.\n" +
                        " Please click on the link below to continue" + "\n" + link);
        javaMailSender.send(mailMessage);

    }

    @Override
    public void sendWelcomeMessageToDriver(RegisterDriverRequest driverDto, String token) {
        String link = baseUrl + "/v1/user/verify?token=" +token;
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(driverDto.getEmail());
        mailMessage.setSubject("Welcome to iTaxi " + driverDto.getName());
        mailMessage.setFrom("info@iTaxi.africa");
        mailMessage.setText(
                "We offer you a chance to make money at your leisure time and your safety is our priority. We hope you enjoy every trip with us.\n" +
                        " Please click on the link below to continue" + "\n" + link);
        javaMailSender.send(mailMessage);


    }

    @Override
    public void sendResetPasswordMail(String email, String token) {
        String link = baseUrl + "/v1/verify-reset-password?token=" +token;

        final SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(email);
        mailMessage.setSubject("Reset Password Confirmation Link!");
        mailMessage.setFrom("Info@iTaxi.africa");
        mailMessage.setText(
                "Please click on the link below to reset your password. \n" + link);
        javaMailSender.send(mailMessage);
    }

    @Override
    public void newTokenMail(String email, String token) {
        String link = baseUrl + "/v1/user/verify?token=" +token;
        final SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(email);
        mailMessage.setSubject("New Verification Link");
        mailMessage.setFrom("info.iTaxi.africa");
        mailMessage.setText(
                "Please click on the below link to verify your account. Link expires in 24 hours" + "\n" + link);
        javaMailSender.send(mailMessage);
    }
}
