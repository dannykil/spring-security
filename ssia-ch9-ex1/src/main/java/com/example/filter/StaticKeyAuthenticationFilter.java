package com.example.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class StaticKeyAuthenticationFilter implements Filter  {

    @Value("${authorization.key}")
    private String authorizationKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("StaticKeyAuthenticationFilter");

        var httpRequest = (HttpServletRequest) request;
        var httpResponse = (HttpServletResponse) response;

        String authentication = httpRequest.getHeader("Authorization");

        // // Filter 순서 확인할 때 아래 전체 주석해서 검증로직 제거
        System.out.println("authentication from authorization.key : " + authorizationKey);
        System.out.println("authentication from       getHeader() : " + authentication);

        if (authorizationKey.equals(authentication)) {
            filterChain.doFilter(request, response);
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        // Filter 순서 확인을 위해 유효성 체크 없이 다음 필터 이동
//        filterChain.doFilter(request, response);
    }
}
