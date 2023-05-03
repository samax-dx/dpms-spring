package com.akcl.dpms.svc_main;

import com.akcl.dpms.svc_main.dto.UserLogin;
import com.akcl.dpms.svc_main.entity.User;
import com.akcl.dpms.svc_main.repository.UserLoginRepository;
import com.akcl.dpms.svc_auth.dto.AuthData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.databind.ObjectMapper;


@ControllerAdvice
@RequiredArgsConstructor
public class UserAdvice {
    private final UserLoginRepository userLoginRepository;

    @ModelAttribute
    public User getUser(@ModelAttribute AuthData authData) {
        UserLogin userLogin = userLoginRepository.findUserLoginByLoginId(authData.loginId).orElse(null);
        if (userLogin == null) {
            return null;
        }
        return new ObjectMapper().convertValue(userLogin, User.class);
    }

}
