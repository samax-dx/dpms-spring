package com.akcl.dpms.svc_auth;

import org.springframework.web.servlet.HandlerInterceptor;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final JwtHelper jwtHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authHeader = request.getHeader("authorization");
        String[] headerParts = (authHeader == null ? "" : authHeader).trim().split(" ");

        if (headerParts.length == 2) {
            request.setAttribute("tokenData", jwtHelper.getTokenData(headerParts[1]));
        } else {
            request.setAttribute("tokenData", new HashMap<>());
        }

        return true;
    }
}
