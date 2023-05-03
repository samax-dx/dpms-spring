package com.akcl.dpms.svc_main;

import com.akcl.dpms.svc_auth.contracts.AuthMessagingService;
import com.akcl.dpms.svc_email.EmailService;
import com.akcl.dpms.svc_email.payload.EmailDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AuthMessagingServiceImpl implements AuthMessagingService {
    private final EmailService emailService;

    @Override
    public void sendMessage(String message, String recipient, String subject) {
        emailService.sendSimpleMail(new EmailDetails(recipient, message, subject));
    }

}
