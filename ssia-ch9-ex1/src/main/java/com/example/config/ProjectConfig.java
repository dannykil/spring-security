package com.example.config;

import com.example.filter.StaticKeyAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        System.out.println("1) UserDetailsService");

        var manager = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("john")
                .password("12345")
                .authorities("READ")
                .roles("ADMIN")
                .build();

        manager.createUser(user1);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("2) PasswordEncoder");

        return NoOpPasswordEncoder.getInstance();
    }


    // ex01 + 전체 응용
//    @Autowired
//    private StaticKeyAuthenticationFilter filter;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.httpBasic();
//
//        http.addFilterBefore(
//                        new RequestValidationFilter(),
//                        BasicAuthenticationFilter.class)
//                .addFilterAfter(
//                        new AuthenticationLoggingFilter(),
//                        BasicAuthenticationFilter.class)
//                .addFilterAt(filter,
//                        BasicAuthenticationFilter.class)
//                .authorizeRequests()
//                .mvcMatchers("/hello").hasRole("ADMIN")
//                .anyRequest()
//                .denyAll();
//    }

    // ex02
    @Autowired
    private StaticKeyAuthenticationFilter filter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(filter,
                        BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .permitAll();
    }
}