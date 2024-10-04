package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
//@Repository
public class CustomAuthenticationProvider implements AuthenticationProvider {
// AuthenticationProvider는 인증 논리를 구현하고 사용자 관리와 암호 관리를 각각 UserDetailsService와 PasswordEncoder에 위임
//public class CustomAuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // AuthenticationProvider를 재정의
    // AuthenticationProvider 클래스를 상속받지 않은 상태에서는 Override조차 되지 않는다(당연히)
    // 구성 클래스(@Configuration)에서 Autowired도 되지 않는다
    // supports(Class<?> authenticationType)도 동일
    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        System.out.println("2) username : " + username);
        System.out.println("3) password : " + password);

        UserDetails u = userDetailsService.loadUserByUsername(username);
        System.out.println("u : " + u); // 사용자가 없는 경우 이

        System.out.println("4) passwordEncoder.matches(password, u.getPassword()) : " + passwordEncoder.matches(password, u.getPassword()));
        if (passwordEncoder.matches(password, u.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, password, u.getAuthorities());
        } else {
            throw new BadCredentialsException("Something went wrong!");
        }
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        System.out.println("1) authenticationType : " + authenticationType);
//        System.out.println("1-1) UsernamePasswordAuthenticationToken.class : " + UsernamePasswordAuthenticationToken.class);
        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
    }
}
