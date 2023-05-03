package com.akcl.dpms.svc_email;

import com.akcl.dpms.svc_email.payload.EmailDetails;


public interface EmailService {
    void sendSimpleMail(EmailDetails details);
}
