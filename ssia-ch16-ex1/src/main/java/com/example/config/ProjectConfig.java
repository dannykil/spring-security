package com.example.config;

import com.example.security.DocumentsPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true) // ex1~ex5 전역 메서드 보안에서 사전/사후 권한 체크 활성화(기본적으로 비활성화 되어있음)
@EnableGlobalMethodSecurity(
        jsr250Enabled = true,
        securedEnabled = true
)
//public class ProjectConfig { // ex1~3
public class ProjectConfig extends GlobalMethodSecurityConfiguration { // ex4

    // ex4 only
    // 시큐리티가 새 DocumentsPermissionEvaluator를 인식할 수 있도록 정의
    @Autowired
    private DocumentsPermissionEvaluator evaluator;


    @Override // createExpressionHandler 재정의
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        var expressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(evaluator);

        return expressionHandler;
    }

    // ex1~3
    @Bean
    public UserDetailsService userDetailsService() {
        var service = new InMemoryUserDetailsManager();

        var u1 = User.withUsername("natalie")
                .password("12345")
                .roles("admin")
//                .authorities("read")
                .build();

        var u2 = User.withUsername("emma")
                .password("12345")
                .roles("manager")
//                .authorities("write")
//                .authorities("write", "read")
                .build();

        service.createUser(u1);
        service.createUser(u2);

        return service;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}

// ex1) 사전 권한 확인
// 1) 불가 : curl -u natalie:12345 http://localhost:8080/hello
// 2) 가능 : curl -u emma:12345 http://localhost:8080/hello

// ex2) 인증된 사용자만 자신의 데이터 확인
// 1) 불가 : curl -u natalie:12345 http://localhost:8080/secret/names/emma
// 2) 가능 : curl -u emma:12345 http://localhost:8080/secret/names/emma

// ex3) 사후 권한 확인
// 1) 불가 : curl -u natalie:12345 http://localhost:8080/book/details/natalie
// 2) 가능 : curl -u natalie:12345 http://localhost:8080/book/details/emma
// 3) 불가 : curl -u emma:12345 http://localhost:8080/book/details/natalie
// 4) 가능 : curl -u emma:12345 http://localhost:8080/book/details/emma
// * reader 항목이 있는 emma는 '모든' 사용자가 조회 가능

// ex4) 메서드 사용 권한 확인
// 1) 가능 : curl -u natalie:12345 http://localhost:8080/documents/abc123
// 2) 가능 : curl -u natalie:12345 http://localhost:8080/documents/qwe123
// 3) 가능 : curl -u natalie:12345 http://localhost:8080/documents/asd555
// 4) 불가 : curl -u emma:12345 http://localhost:8080/documents/abc123
// 5) 불가 : curl -u emma:12345 http://localhost:8080/documents/qwe123
// 6) 가능 : curl -u emma:12345 http://localhost:8080/documents/asd555
// * admin role이 있는 사용자는 전체 조회 가능, 아닌 경우 본인의 자료만 조회 가능
