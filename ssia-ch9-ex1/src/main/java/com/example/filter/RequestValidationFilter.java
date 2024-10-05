package com.example.filter;

import javax.servlet.*;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestValidationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("RequestValidationFilter");

        var httpRequest = (HttpServletRequest) request;
        var httpResponse = (HttpServletResponse) response;
        String requestId = httpRequest.getHeader("Request-Id");

        if (requestId == null || requestId.isBlank()) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 헤더가 없으면 HttpStatus 400 리턴
            return;
        }

//        System.out.println("Request-Id : " + requestId);
//        System.out.println("request : " + ((HttpServletRequest) request).getHeader("Authorization"));
//        System.out.println("response : " + ((HttpServletResponse) response).getStatus());
        filterChain.doFilter(request, response); // 요청을 필터 체인의 다음 필터로 전달
    }
}
