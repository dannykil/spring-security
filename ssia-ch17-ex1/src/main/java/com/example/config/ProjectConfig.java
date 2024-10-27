package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ProjectConfig {

    // ex5)
    // 의존성 spring-security-data 이 추가되어야 함
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var uds = new InMemoryUserDetailsManager();

        var u1 = User.withUsername("nikolai")
                .password("12345")
                .authorities("read")
                .build();

        var u2 = User.withUsername("julien")
                .password("12345")
                .authorities("write")
                .build();

        uds.createUser(u1);
        uds.createUser(u2);

        return uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}

// ex1) 로그인한(인증된) 사용자의 데이터만 조회되는지 확인(사전필터링)
// 1) curl -u nikolai:12345 http://localhost:8080/sell
// 2) curl -u julien:12345 http://localhost:8080/sell

// ex2) List.of()는 변경이 불가하기 때문에 500에러 확인
// 1) curl -u nikolai:12345 http://localhost:8080/sell
// 2) curl -u julien:12345 http://localhost:8080/sell

// ex3) 로그인한(인증된) 사용자의 데이터만 조회되는지 확인(사후필터링)
// 1) curl -u nikolai:12345 http://localhost:8080/find
// 2) curl -u julien:12345 http://localhost:8080/find

// ex4) 로그인한(인증된) 사용자의 데이터만 조회되는지 확인(사후필터링)
// 1) curl -u nikolai:12345 http://localhost:8080/products/c
// 2) curl -u julien:12345 http://localhost:8080/products/c


//curl -XPOST "http://localhost:8080/realms/master/protocol/openid-connect/token" -H "Content-Type: application/x-www-form-urlencoded" --data-urlencode "grant_type=password" --data-urlencode "username=rachel" --data-urlencode "password=12345" --data-urlencode "scope=fitnessapp" --data-urlencode "client_id=fitnessapp"