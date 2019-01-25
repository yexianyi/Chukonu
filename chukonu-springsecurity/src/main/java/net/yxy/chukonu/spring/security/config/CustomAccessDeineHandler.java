package net.yxy.chukonu.spring.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeineHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws ServletException, IOException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Your Authenticaton Level is NOT granted to access this resource!");
    }

}
