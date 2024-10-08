package com.example.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class InMemoryUserDetailsService implements UserDetailsService {

    private final List<UserDetails> users;

    public InMemoryUserDetailsService(List<UserDetails> users) {
        System.out.println("InMemoryUserDetailsService");
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername : " + username);

        return users.stream()
                .filter(
                        user -> user.getUsername().equals(username)
                )
                .findFirst()
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found")
                );
    }
}
