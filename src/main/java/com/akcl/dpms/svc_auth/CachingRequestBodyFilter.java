package com.akcl.dpms.svc_auth;

import com.akcl.dpms.util.CachedBodyHttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
public class CachingRequestBodyFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest currentRequest = (HttpServletRequest) servletRequest;
        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(currentRequest);
        chain.doFilter(wrappedRequest, servletResponse);
    }

}