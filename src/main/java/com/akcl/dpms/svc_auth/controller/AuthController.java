package com.akcl.dpms.svc_auth.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.akcl.dpms.svc_auth.JwtHelper;
import com.akcl.dpms.svc_auth.PasswordHelper;
import com.akcl.dpms.svc_auth.annotations.Authorize;
import com.akcl.dpms.svc_auth.contracts.AuthMessagingService;
import com.akcl.dpms.svc_auth.contracts.SignupPersistenceService;
import com.akcl.dpms.svc_auth.entity.LoginBase;
import com.akcl.dpms.svc_auth.dto.AuthData;
import com.akcl.dpms.svc_auth.dto.Password;
import com.akcl.dpms.svc_auth.dto.OtpRecipient;
import com.akcl.dpms.svc_auth.dto.SignupCredential;
import com.akcl.dpms.svc_auth.repository.LoginBaseRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/Auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtHelper jwtHelper;
    private final PasswordHelper passwordHelper;
    private final LoginBaseRepository loginRepository;
    private final AuthMessagingService authMessagingService;
    private final SignupPersistenceService signupPersistenceService;

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/sendSignupOtp",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public Object sendSignupOtp(@RequestBody OtpRecipient otpRecipient) {
        String recipientAddress = Optional.ofNullable(otpRecipient.address).orElse("");

        try {
            Password otp = passwordHelper.createPassword(String.valueOf((long) Math.floor(Math.random() * 1000000)), recipientAddress);
            authMessagingService.sendMessage("Verification Code: " + otp.plainVal, recipientAddress, "Recipient Verification");

            return ImmutableMap.of("token", jwtHelper.tokenFromDataMap(ImmutableMap.of("loginId", recipientAddress, "password", otp.hashVal)));
        } catch (Exception e) {
            if (e instanceof MailParseException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }

    @Authorize
    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/signup",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public Object signup(@RequestBody SignupCredential signupCredential, @RequestBody Map<String, Object> signupData, @ModelAttribute AuthData authData) {
        Password tempPassword;
        Password inputPassword;
        String inputSalt = RandomStringUtils.randomAscii(8);

        try {
            String signupOtp = Optional.ofNullable(signupCredential.otp).orElseThrow(Exception::new);
            tempPassword = new Password(signupOtp, authData.password);

            String signupPassword = Optional.ofNullable(signupCredential.password).orElseThrow(Exception::new);
            inputPassword = passwordHelper.createPassword(signupPassword, inputSalt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid_credentials");
        }

        try {
            passwordHelper.validatePassword(tempPassword, authData.loginId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid_credentials");
        }

        LoginBase login = new LoginBase();
        login.setLoginId(authData.loginId);
        login.setSalt(inputSalt);
        login.setPassword(inputPassword.hashVal);

        try {
            signupPersistenceService.handleSignup(signupData, new ObjectMapper().convertValue(login, new TypeReference<Map<String, Object>>() {}));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ImmutableMap.of("token", jwtHelper.tokenFromDataMap(ImmutableMap.of("loginId", login.getLoginId())));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public Object login(@RequestBody AuthData authData) {
        LoginBase login = loginRepository.findById(authData.loginId).orElse(null);
        if (login == null)  {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user_not_found");
        }

        try {
            passwordHelper.validatePassword(new Password(authData.password, login.getPassword()), login.getSalt());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("authorization_failed");
        }

        return ImmutableMap.of("token", jwtHelper.tokenFromDataMap(ImmutableMap.of("loginId", login.getLoginId())));
    }
}
