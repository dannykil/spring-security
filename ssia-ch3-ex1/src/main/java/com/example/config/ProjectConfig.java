package com.example.config;

import com.example.model.User;
import com.example.service.InMemoryUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;
import java.util.List;

// ex01 - InMemoryUserDetailsService
// @Configuration를 삽입하지 않았더니 시큐리티 기본 설정만 적용되고 아래 설정은 적용되지 않음
//@Configuration
//public class ProjectConfig {
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        System.out.println("ProjectConfig");
//        UserDetails user = new User("john", "12345", "read");
//
//        List<UserDetails> users = List.of(user);
//        return new InMemoryUserDetailsService(users);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
//}

// ex02 - JdbcUserDetailsManager
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        String usersByUsernameQuery = "select username, password, enabled from spring.users where username = ?";
        String authsByUserQuery = "select username, authority from spring.authorities where username = ?";

        var userDetailsManager = new JdbcUserDetailsManager(dataSource);

        userDetailsManager.setUsersByUsernameQuery(usersByUsernameQuery);
        userDetailsManager.setAuthoritiesByUsernameQuery(authsByUserQuery);

        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}