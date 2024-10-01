//package com.example.config;
//
//import com.example.security.CustomAuthenticationProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//@Configuration
//// WebSecurityConfigurerAdapter 클래스를 확장하면 configure 메서드를 재정의할 수 있음(지워도 오류는 안나는데...?)
//public class ProjectConfig extends WebSecurityConfigurerAdapter {
////public class ProjectConfig {
//
//    // 방법1) 빈에 UserDetailsService와 PasswordEncoder를 정의
//    // * UserManagementConfig으로 분리
////    @Bean
////    public UserDetailsService userDetailsService() {
////        var userDetailsService = new InMemoryUserDetailsManager();
////
////        var user = User.withUsername("john")
////                .password("12345")
////                .authorities("read")
////                .build();
////
////        userDetailsService.createUser(user);
////
////        return userDetailsService;
////    }
////
////    @Bean
////    public PasswordEncoder passwordEncoder(){
////        // 기본 UserDetailsService를 이용하면 PasswordEncoder도 자동 구성되지만
////        // UserDetailsService를 재정의하면 PasswordEncoder도 다시 선언해야함
////        // 선언하지 않는 경우 There is no PasswordEncoder mapped for the id "null" 이라는 Exception 발생
////        return NoOpPasswordEncoder.getInstance();
////    }
//
//
//    // 방법2) configure 메소드에 UserDetailsService와 PasswordEncoder를 정의
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////
////        var userDetailsService = new InMemoryUserDetailsManager(); // InMemoryUserDetailsManager 인스턴스 생성
////
////        var user = User.withUsername("john")
////                .password("12345")
////                .authorities("read")
////                .build();
////
////        userDetailsService.createUser(user); // 사용자 추가
////
////        auth.userDetailsService(userDetailsService). // userDetailsService 구성
////                passwordEncoder(NoOpPasswordEncoder.getInstance()); // passwordEncoder 구성
////    }
//
//    // 방법3) 가능하면 애플리케이션의 책임을 분리하는 것이 좋기 때문에 권장하지 않음
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////
////        auth.inMemoryAuthentication()
////                    .withUser("john")
////                    .password("12345")
////                    .authorities("read")
////                .and()
////                    .passwordEncoder(NoOpPasswordEncoder.getInstance());
////    }
//
//    // 방법4) AuthenticationProvider
//    @Autowired
//    private CustomAuthenticationProvider authenticationProvider;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        System.out.println("ProjectConfig");
//        auth.authenticationProvider(authenticationProvider);
//    }
//
//    // 이 부분을 추가하니 /login 경로로 들어가면 로그인 화면이 아닌 자격증명을 위한 창이 뜨게 됨
//    // + /logout도 구동안됨
//    // >>> configure를 사용하면 로그인/로그아웃도 모두 구현해야 하는가?
//    // * WebAuthorizationConfig으로 분리
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http.httpBasic();
////        http.authorizeRequests()
////                .anyRequest().authenticated(); // 모든 요청에 인증 요구
//////                .anyRequest().permitAll(); // 모두 허가
////    }
//}
