package com.example.authentication.providers;

import com.example.authentication.UsernamePasswordAuthentication;
import com.example.authentication.proxy.AuthenticationServerProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AuthenticationServerProxy proxy;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("\n ### UsernamePasswordAuthenticationProvider > authenticate");

        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        proxy.sendAuth(username, password);
        return new UsernamePasswordAuthenticationToken(username, password);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        System.out.println("\n ### UsernamePasswordAuthenticationProvider > supports");
        return UsernamePasswordAuthentication.class.isAssignableFrom(aClass);
    }
}