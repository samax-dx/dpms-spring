package com.akcl.dpms.svc_main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.akcl.dpms.svc_auth.contracts.SignupPersistenceService;
import com.akcl.dpms.svc_main.entity.Login;
import com.akcl.dpms.svc_main.entity.User;
import com.akcl.dpms.svc_main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@RequiredArgsConstructor
public class SignupPersistenceServiceImpl implements SignupPersistenceService {
    private final UserRepository userRepository;

    @Override
    public void handleSignup(Map<String, Object> signupData, Map<String, Object> loginData) throws Exception {
        User user = new ObjectMapper().convertValue(signupData, User.class);
        Login login = new ObjectMapper().convertValue(loginData, Login.class);

        user.setLogin(login);
        login.setUser(user);

        userRepository.save(user);
    }

}
