package com.akcl.dpms.svc_auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class JwtHelper {
    private final String jwtSecret;

    public Map<String, String> getTokenData(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            Algorithm.HMAC256(jwtSecret).verify(decodedJWT);

            if (decodedJWT.getExpiresAt().after(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))) {
                return new HashMap<>();
            }

            return decodedJWT
                    .getClaims()
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().as(String.class)));
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public String getDataToken(Map<String, String> tokenData) {
        JWTCreator.Builder jwtBuilder = JWT.create();

        for (Map.Entry<String, String> tokenEntry: tokenData.entrySet()) {
            jwtBuilder = jwtBuilder.withClaim(tokenEntry.getKey(), tokenEntry.getValue());
        }
        jwtBuilder = jwtBuilder.withExpiresAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        return jwtBuilder.sign(Algorithm.HMAC256(jwtSecret));
    }
}
