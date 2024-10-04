package com.example.handlers;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)  {
        System.out.println("httpServletRequest : " + httpServletRequest.getRequestURI());
        System.out.println("httpServletResponse : " + httpServletResponse.getStatus());
        System.out.println("AuthenticationException : " + e.getMessage());

        // 개발자도구 > 네트워크 > 헤더 > 응답헤더에서 확인 가능
        httpServletResponse.setHeader("failed", LocalDateTime.now().toString());
    }
}
