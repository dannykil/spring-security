package com.example.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

//public class AuthenticationLoggingFilter implements Filter {
// ex03 - OncePerRequestFilter 클래스로 확장 >>> Http 필터만 지원
public class AuthenticationLoggingFilter extends OncePerRequestFilter {
    // OncePerRequestFilter : 필터가 두번 이상 호출되지 않도록 보장

    private final Logger logger =
            Logger.getLogger(AuthenticationLoggingFilter.class.getName());

    //    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    // ex03
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("AuthenticationLoggingFilter");

//        var httpRequest = (HttpServletRequest) request;
//        String requestId = httpRequest.getHeader("Request-Id");
        // ex03
        String requestId = request.getHeader("Request-Id");

        logger.info("Successfully authenticated request with id " +  requestId);

        filterChain.doFilter(request, response);
    }
}
