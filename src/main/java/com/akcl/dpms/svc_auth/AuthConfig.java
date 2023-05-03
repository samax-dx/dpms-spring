package com.akcl.dpms.svc_auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
public class AuthConfig implements WebMvcConfigurer {
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(new JwtHelper(secret)));
    }

    @Bean
    public JwtHelper jwtHelper() {
        return new JwtHelper(secret);
    }

    @Bean
    public PasswordHelper passwordHelper() {
        return new PasswordHelper(secret);
    }

}
