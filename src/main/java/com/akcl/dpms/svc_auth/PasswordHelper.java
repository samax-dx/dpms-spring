package com.akcl.dpms.svc_auth;

import com.akcl.dpms.svc_auth.models.Password;
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

    public Password createPassword(String password, String salt) throws Exception {
        String passwordHash = hashPassword(password, salt);
        return new Password(password, passwordHash);
    }

    public void validatePassword(Password password, String salt) throws Exception {
        String passwordHash = hashPassword(password.plainVal, salt);
        if (!passwordHash.equals(password.hashVal)) {
            throw new Exception("validation_failed");
        }
    }

    private String hashPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), mac.getAlgorithm()));
        return new BigInteger(1, mac.doFinal((password + ":" + salt).getBytes(StandardCharsets.UTF_8))).toString(16);
    }
}
