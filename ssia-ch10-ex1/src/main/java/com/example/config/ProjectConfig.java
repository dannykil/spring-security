package com.example.config;

import com.example.csrf.CustomCsrfTokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    // ex01
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterAfter(
//                        new CsrfTokenLogger(),
//                        CsrfFilter.class)
//                .authorizeRequests()
//                .anyRequest().permitAll();
//
//        // * 중요 : 쿠키값도 헤더이 있어야한다(포스트맨에서는 자동으로 저장되어 있음)
//        // 1) 200 : curl -XPOST -H "Cookie:JSESSIONID=1FC93B7F98DDACAABB2DC4668128CBB0" -H "X-CSRF-TOKEN:3051f071-01ea-4c95-9cf1-d7122ef5ee75" http://localhost:8080/hello
//        // 2) 403 : curl -XPOST -H "Cookie:JSESSIONID=1FC93B7F98DDACAABB2DC4668128CBB0" http://localhost:8080/hello
//        // 3) 403 : curl -XPOST -H "X-CSRF-TOKEN:3051f071-01ea-4c95-9cf1-d7122ef5ee75" http://localhost:8080/hello
//    }

    // ex02
//    @Bean
//    public UserDetailsService uds() {
//        var uds = new InMemoryUserDetailsManager();
//
//        var u1 = User.withUsername("mary")
//                .password("12345")
//                .authorities("READ")
//                .build();
//
//        uds.createUser(u1);
//
//        return uds;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.authorizeRequests()
//                .anyRequest().authenticated();
//
//        http.formLogin()
//                .defaultSuccessUrl("/main", true);
//    }

    // ex03
    @Bean
    public CsrfTokenRepository customTokenRepository() {
        return new CustomCsrfTokenRepository();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf(c -> {
            c.csrfTokenRepository(customTokenRepository());
            c.ignoringAntMatchers("/ciao"); // /ciao만 csrf 비활성화

//            HandlerMappingIntrospector i = new HandlerMappingIntrospector();
//            MvcRequestMatcher r = new MvcRequestMatcher(i, "/ciao");
//            c.ignoringRequestMatchers(r);

//            String pattern = ".*[0-9].*";
//            String httpMethod = HttpMethod.POST.name();
//            RegexRequestMatcher r = new RegexRequestMatcher(pattern, httpMethod);
//            c.ignoringRequestMatchers(r);
        });

        http.authorizeRequests()
                .anyRequest().permitAll();
    }
}