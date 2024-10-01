package com.example.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    // AuthenticationProvider는 인증 논리를 구현하고 사용자 관리와 암호 관리를
    // 각각 UserDetailsService와 PasswordEncoder에 위임

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // authenticate 메서드는 인증의 전체 논리를 나타냄

        String username = authentication.getName(); // Principal 인터페이스의 getName() 메서드를 Authentication에서 상속받음
        String password = String.valueOf(authentication.getCredentials());

        if ("john".equals(username) && "12345".equals(password)) {
            // UserDetailsService와 PasswordEncoder를 호출해서 사용자 이름과 암호 테스트

            return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList());
        } else {
            throw new AuthenticationCredentialsNotFoundException("Error in authentication");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) { // supports 메서드는 5장에서 다룸

        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
