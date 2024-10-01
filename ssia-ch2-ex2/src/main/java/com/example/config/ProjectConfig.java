package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        var userDetailsService = new InMemoryUserDetailsManager();

        var user = User.withUsername("john")
                .password("12345")
                .authorities("read")
                .build();
        userDetailsService.createUser(user);

        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        // 기본 UserDetailsService를 이용하면 PasswordEncoder도 자동 구성되지만
        // UserDetailsService를 재정의하면 PasswordEncoder도 다시 선언해야함
        // 선언하지 않는 경우 There is no PasswordEncoder mapped for the id "null" 이라는 Exception 발생
        return NoOpPasswordEncoder.getInstance();
    }
}
