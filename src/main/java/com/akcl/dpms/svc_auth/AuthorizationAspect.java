package com.akcl.dpms.svc_auth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.akcl.dpms.svc_auth.repository.LoginBaseRepository;
import com.akcl.dpms.svc_auth.annotations.Authorize;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Aspect
@Service
@RequiredArgsConstructor
public class AuthorizationAspect {

    private final HttpServletRequest httpServletRequest;

    private final LoginBaseRepository loginBaseRepository;

    @Around(value = "@annotation(authorizeAnnotation)")
    public Object authorize(ProceedingJoinPoint joinPoint, Authorize authorizeAnnotation) {
        try {
            Map<String, String> user = new ObjectMapper().convertValue(Optional.ofNullable(httpServletRequest.getAttribute("authData")).orElse(new HashMap<>()),
                    new TypeReference<Map<String, String>>() {});

            boolean authChecks;

            authChecks = user.containsKey("loginId") && (user.containsKey("password") || loginBaseRepository.findById(user.get("loginId")).orElse(null) != null);
            authChecks &= hasPartyRoles(authorizeAnnotation.roles(), user);
            authChecks &= belongsToPartyGroups(authorizeAnnotation.groups(), user);

            if (authChecks) {
                return joinPoint.proceed();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("authorization_failed");
            }
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private boolean hasPartyRoles(String[] partyRoles, Map<String, String> party) {
        return true;
    }

    private boolean belongsToPartyGroups(String[] partyGroups, Map<String, String> party) {
        return true;
    }

}
