package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

//@Controller
@RestController
public class MainController {

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/main")
    public String main(Authentication authentication) {
        System.out.println("token                : " + authentication);
        return "Hello World";
    }
}
