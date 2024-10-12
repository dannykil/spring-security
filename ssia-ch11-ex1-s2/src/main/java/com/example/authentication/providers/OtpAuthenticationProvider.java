package com.example.authentication.providers;

import com.example.authentication.OtpAuthentication;
import com.example.authentication.proxy.AuthenticationServerProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AuthenticationServerProxy proxy;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("\n ### OtpAuthenticationProvider > authenticate");

        String username = authentication.getName();
        String code = String.valueOf(authentication.getCredentials());
        boolean result = proxy.sendOTP(username, code);
        System.out.println("username : " + username);
        System.out.println("code.    : " + code);
        System.out.println("result   : " + result);


        if (result) {
            return new OtpAuthentication(username, code);
        } else {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        System.out.println("\n ### OtpAuthenticationProvider > supports");

        return OtpAuthentication.class.isAssignableFrom(aClass);
    }
}