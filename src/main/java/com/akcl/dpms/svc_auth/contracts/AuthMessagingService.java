package com.akcl.dpms.svc_auth.contracts;

public interface AuthMessagingService {
    void sendMessage(String message, String recipient, String subject);
}
