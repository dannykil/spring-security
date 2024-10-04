package com.example.config;

import com.example.handlers.CustomAuthenticationFailureHandler;
import com.example.handlers.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableAsync // 2024-10-04
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    // APPLICATION FAILED TO START
    // The dependencies of some of the beans in the application context form a cycle
    // AuthenticationProvider를 연결시키면 위와 같은 에러와 함께 애플리케이션이 실행되지 않음(configure도 동일) > 주석처리 해야함
//    @Autowired
//    private AuthenticationProvider authenticationProvider;

    // APPLICATION FAILED TO START
    // The dependencies of some of the beans in the application context form a cycle
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(authenticationProvider);
//    }

    // 구성 클래스에서 UserDetailsService, PasswordEncoder 선언되는 순서가 달라도
    // AuthenticationProvider가 Override되는 클래스에 정의되는 순서에 맞춰짐
    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("2) PasswordEncoder");
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        System.out.println("1) UserDetailsService");
        return new JdbcUserDetailsManager(dataSource);
    }

    // 2024-10-04
    // ssia-ch5-ex02
    // 엔드포인트가 비동기가 되면 메서드를 실행하는 스레드와 요청을 수행하는 스레드가 다른 스레드가 되며
    // 요청을 수행하는 스레드에서는 보안 컨텍스트를 상속하지 않았기 때문에 NullPointerExceptiondl 발생함
    // 이 문제를 해결하기위한 전략이 MODE_INHERITABLETHREADLOCAL
    // * DelegatingSecurityContextCallable를 테스트하기 위해서는 주석처리 해야함
//    @Bean
//    public InitializingBean initializingBean() {
////        return () -> SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
//        return () -> SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
//    }

    // 2024-10-04
    // ssia-ch5-ex03
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic(c -> {
//            c.realmName("OTHER");
//            c.authenticationEntryPoint(new CustomEntryPoint());
//        });
//        http.authorizeRequests()
//                .anyRequest()
//                .authenticated();
//    }

    // 2024-10-04
    // ssia-ch5-ex04
    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
//                .defaultSuccessUrl("/home", true);
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .httpBasic();

        http.authorizeRequests()
                .anyRequest()
                .authenticated();
    }
}