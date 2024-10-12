package com.example.config;

import com.example.authentication.filters.InitialAuthenticationFilter;
import com.example.authentication.filters.JwtAuthenticationFilter;
import com.example.authentication.providers.OtpAuthenticationProvider;
import com.example.authentication.providers.UsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private InitialAuthenticationFilter initialAuthenticationFilter;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private OtpAuthenticationProvider otpAuthenticationProvider;

    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        System.out.println("\n ### 2) SecurityConfig > configure(AuthenticationManagerBuilder)");

        // 선언하는 순서가 문제되지 않음
//        auth.authenticationProvider(otpAuthenticationProvider)
//                .authenticationProvider(usernamePasswordAuthenticationProvider);
        auth.authenticationProvider(usernamePasswordAuthenticationProvider)
                .authenticationProvider(otpAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("\n ### 3) SecurityConfig > configure(HttpSecurity)");

        http.csrf().disable();

        http.addFilterAt(
                        initialAuthenticationFilter,
                        BasicAuthenticationFilter.class)
                .addFilterAfter(
                        jwtAuthenticationFilter,
                        BasicAuthenticationFilter.class
                );

        http.authorizeRequests()
                .anyRequest().authenticated();
    }

    //    The default strategy is if configure(AuthenticationManagerBuilder) method is overridden to use the AuthenticationManagerBuilder that was passed in
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        System.out.println("\n ### 1) SecurityConfig > authenticationManager");

        return super.authenticationManager();
    }
}