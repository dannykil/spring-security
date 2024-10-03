package com.example.config;

import com.example.model.User;
import com.example.service.InMemoryUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

// @Configuration를 삽입하지 않았더니 시큐리티 기본 설정만 적용되고 해당 파일의 설정은 완전히 무시됨
@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        System.out.println("ProjectConfig");
        UserDetails user = new User("john", "12345", "read");

        List<UserDetails> users = List.of(user);
        return new InMemoryUserDetailsService(users);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
