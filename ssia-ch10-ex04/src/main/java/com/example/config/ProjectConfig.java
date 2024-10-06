package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 아래 코드를 주석하면 127.0.0.1:8080/test 호출 시 CORS오류 발생
        // CORS 정책 적용방법2
//        http.cors(c -> {
//            CorsConfigurationSource source = request -> {
//                CorsConfiguration config = new CorsConfiguration();
//                config.setAllowedOrigins(List.of("*"));
//                config.setAllowedMethods(List.of("*"));
//                return config;
//            };
//            c.configurationSource(source);
//        });

        http.csrf().disable();

        http.authorizeRequests()
                .anyRequest().permitAll();
    }
}