package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .authorities("READ")
//                .authorities("ROLE_ADMIN")
                .roles("ADMIN")
                .build();

        var user2 = User.withUsername("jane")
                .password("12345")
                .authorities("WRITE", "READ", "DELETE", "PREMIUM")
//                .authorities("ROLE_MANAGER")
//                .roles("MANAGER")
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
        System.out.println("3) configure");

        http.httpBasic();

//        http.authorizeRequests()
//                .mvcMatchers("/hello").hasRole("ADMIN")
//                .mvcMatchers("/ciao").hasRole("MANAGER")
//                .anyRequest().permitAll(); // 없어도 다른 엔드포인트에 대해서는 모든 사용자가 접근 가능하나 모든 규칙은 명시적으로 지정하는 것이 좋다

//        http.authorizeRequests()
//                .mvcMatchers(HttpMethod.GET, "/a")
//                .authenticated()
//                .mvcMatchers(HttpMethod.POST, "/a")
//                .permitAll()
//                .mvcMatchers("/product/{code:^[0-9]*$}")
//                .permitAll()
//                .anyRequest()
//                .denyAll();
//
//        http.csrf().disable(); // POST 방식으로 /a 경로를 호출할 수 있게 CSRF 비활성화

//        http.authorizeRequests()
//                .mvcMatchers("/hello")
//                .authenticated()
//                .antMatchers("/ciao")
//                .authenticated();
                // * 중요
                // 1) 401 : curl http://localhost:8080/hello
                // 2) 401 : curl http://localhost:8080/hello/
                // 3) 401 : curl http://localhost:8080/ciao
                // * 4) 인증없이 정상호출 됨 : curl http://localhost:8080/ciao/
                // >>> mvc 선택기를 이용하는 것이 좋다

        // java.lang.IllegalArgumentException: No capture groups allowed in the constraint regex: .*(.+@.+\.com)
//        http.authorizeRequests()
//                .mvcMatchers("/email/{email:.*(.+@.+\\.com)}")
//                .permitAll()
//                .anyRequest()
//                .denyAll();

        http.authorizeRequests()
                .regexMatchers(".*/(us|uk|ca)+/(en|fr).*")
                .authenticated()
                .anyRequest().hasAuthority("PREMIUM");
        // 1) 200 : curl -u john:12345 http://localhost:8080/video/us/en
        // 2) 200 : curl -u john:12345 http://localhost:8080/video/us/ena (마지막 엔드포인트는 *가 적용되는듯)
        // 3) 403 : curl -u john:12345 http://localhost:8080/video/usa/en
        // 4) 200 : curl -u jane:12345 http://localhost:8080/video/us/en
        // 5) 200 : curl -u jane:12345 http://localhost:8080/video/us/ena
        // 6) 200 : curl -u jane:12345 http://localhost:8080/video/usa/en
    }
}