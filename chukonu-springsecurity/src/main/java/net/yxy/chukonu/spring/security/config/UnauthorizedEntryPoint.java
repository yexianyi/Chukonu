package net.yxy.chukonu.spring.security.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public final class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        if (isHtmlRequest(request)) {
            response.sendRedirect("login.html");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }

    }

    public static boolean isHtmlRequest(HttpServletRequest request) {
        String contentType = request.getHeader("Content-Type");
        if(contentType == null) {
            return true ;
        } else {
            return contentType.contains("text/html") ;
        }
    }
}