package com.akcl.dpms.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


public class SpringWebUtil {
    public static Object currentRequestAttribute(String attributeName) {
        return RequestContextHolder.currentRequestAttributes().getAttribute(attributeName, RequestAttributes.SCOPE_REQUEST);
    }

    public static Object currentSessionAttribute(String attributeName) {
        return RequestContextHolder.currentRequestAttributes().getAttribute(attributeName, RequestAttributes.SCOPE_SESSION);
    }

    public static void setCurrentRequestAttribute(String attributeName, Object attributeValue) {
        RequestContextHolder.currentRequestAttributes().setAttribute(attributeName, attributeValue, RequestAttributes.SCOPE_REQUEST);
    }

    public static void setCurrentSessionAttribute(String attributeName, Object attributeValue) {
        RequestContextHolder.currentRequestAttributes().setAttribute(attributeName, attributeValue, RequestAttributes.SCOPE_SESSION);
    }

    public static String getSupportedProtocolScheme(String host) {
        String[] hostParts = host.split(":");

        String hostname = hostParts[0];
        int port = Integer.parseInt(hostParts.length > 1 ? hostParts[1] : "443");

        try {
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) factory.createSocket(hostname, port);

            socket.startHandshake();
            socket.close();

            return "https";
        } catch (Exception ignore) {
            return "http";
        }
    }
}
