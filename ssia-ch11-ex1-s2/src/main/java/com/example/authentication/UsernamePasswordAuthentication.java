package com.example.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UsernamePasswordAuthentication extends UsernamePasswordAuthenticationToken {

    public UsernamePasswordAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        System.out.println("\n ### UsernamePasswordAuthentication > UsernamePasswordAuthentication(p3)");
    }

    public UsernamePasswordAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
        System.out.println("\n ### UsernamePasswordAuthentication > UsernamePasswordAuthentication(p2)");
        System.out.println("principal.  : " + principal);
        System.out.println("credentials : " + credentials);
    }
}