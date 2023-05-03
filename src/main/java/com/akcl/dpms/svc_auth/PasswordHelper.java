package com.akcl.dpms.svc_auth;

import com.akcl.dpms.svc_auth.dto.PasswordPair;
import lombok.RequiredArgsConstructor;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;


@RequiredArgsConstructor
public class PasswordHelper {
    private final String secret;

    public PasswordPair createPasswordPair(String password, String salt) throws Exception {
        String passwordHash = hashPassword(password, salt);
        return new PasswordPair(password, passwordHash);
    }

    public void validatePasswordPair(PasswordPair passwordPair, String salt) throws Exception {
        String passwordHash = hashPassword(passwordPair.password, salt);
        if (!passwordHash.equals(passwordPair.passwordHash)) {
            throw new Exception("validation_failed");
        }
    }

    private String hashPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), mac.getAlgorithm()));
        return new BigInteger(1, mac.doFinal((password + ":" + salt).getBytes(StandardCharsets.UTF_8))).toString(16);
    }
}
