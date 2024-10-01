package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebAuthorizationConfig extends WebSecurityConfigurerAdapter  {
    // ProjectConfig, WebAuthorizationConfig 두개의 config 클래스에서
    // WebSecurityConfigurerAdapter를 확장하면 Exception이 발생하기 때문에
    // ProjectConfig은 전체 주석처리

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("WebAuthorizationConfig");

        http.httpBasic();
        http.authorizeRequests()
                .anyRequest().authenticated(); // 모든 요청에 인증 요구
//                .anyRequest().permitAll(); // 모두 허가
    }
}
