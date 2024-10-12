package com.example.controller;

import com.example.entities.Otp;
import com.example.entities.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    // 확인
    @PostMapping("/user/add")
    public void addUser(@RequestBody User user) {
        System.out.println("\n### AuthController > addUser");
        System.out.println("addUser : " + user.getUsername()); // username이 PRIMARY KEY라 중복 Insert 안됨
        userService.addUser(user);
    }

    // 확인
    @PostMapping("/user/auth")
    public void auth(@RequestBody User user) {
        System.out.println("\n### AuthController > auth");
        System.out.println("addUser : " + user.getUsername());

        userService.auth(user);
    }

    @PostMapping("/otp/check")
    public void check(@RequestBody Otp otp, HttpServletResponse response) {
        System.out.println("\n### AuthController > check");

        if (userService.check(otp)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}