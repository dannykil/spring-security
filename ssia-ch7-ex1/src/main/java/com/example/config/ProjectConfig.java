package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        System.out.println("1) UserDetailsService");

        var manager = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("john")
                .password("12345")
//                .authorities("READ")
//                .authorities("ROLE_ADMIN")
                .roles("ADMIN")
                .build();

        var user2 = User.withUsername("jane")
                .password("12345")
//                .authorities("WRITE", "READ", "DELETE")
//                .authorities("ROLE_MANAGER")
                .roles("MANAGER")
                .build();

        manager.createUser(user1);
        manager.createUser(user2);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("2) PasswordEncoder");

        return NoOpPasswordEncoder.getInstance();
    }

    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("3) HttpSecurity");

        http.httpBasic();

        String expression = "hasAuthority('READ') and !hasAuthority('DELETE')";
        http.authorizeRequests()
                .anyRequest()
//                .antMatchers().permitAll();
//                .hasAuthority("WRITE");
//                .hasAnyAuthority("WRITE","READ");
//                .access("hasAuthority('WRITE')");
//                .access("hasAnyAuthority('READ', 'WRITE')"); // 사용자에게 READ 또는 WRITE 권한이 필요하다고 지정
//                .access(expression);
//                .hasRole("ADMIN");
                .denyAll();
    }
}