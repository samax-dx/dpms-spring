package com.akcl.dpms.svc_email;

import com.akcl.dpms.svc_email.payload.EmailDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendSimpleMail(EmailDetails details) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.recipient);
            mailMessage.setText(details.message);
            mailMessage.setSubject(details.subject);

            javaMailSender.send(mailMessage);
    }
}
