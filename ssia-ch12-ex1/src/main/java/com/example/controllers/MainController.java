package com.example.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Logger;

@Controller
public class MainController {

    private Logger logger = Logger.getLogger(MainController.class.getName());

    @GetMapping("/")
    public String main(OAuth2AuthenticationToken token) {
        logger.info(String.valueOf(token));
        logger.info(String.valueOf(token.getPrincipal()));
        System.out.println("token                : " + token);
        System.out.println("token.getPrincipal() : " + token.getPrincipal());
        return "main.html";
    }
}
