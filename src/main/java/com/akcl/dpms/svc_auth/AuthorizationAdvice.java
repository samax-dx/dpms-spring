package com.akcl.dpms.svc_auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.akcl.dpms.svc_auth.dto.AuthData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.Map;


@ControllerAdvice
public class AuthorizationAdvice {

    @ModelAttribute
    public AuthData getAuthData(@RequestAttribute Map<String, Object> authData) {
        return new ObjectMapper().convertValue(authData, AuthData.class);
    }

}
