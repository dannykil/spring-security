package com.example.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // ex01, ex03
//@Controller // ex02
public class HelloController {

	// ex01
//	@GetMapping("/hello")
//	public String getHello(Authentication authentication) {
//		System.out.println("get hello");
//
//		return "Get Hello!";
//	}
//
//	@PostMapping("/hello")
//	public String postHello(Authentication authentication) {
//		System.out.println("post hello");
//
//		return "Post Hello!";
//	}

	// ex02
//	@GetMapping("/main")
//	public String main() {
//		System.out.println("main");
//
//		return "main.html";
//	}

	// ex03
	@GetMapping("/hello")
	public String getHello(Authentication authentication) {
		System.out.println("get hello");

		return "Get Hello!";
	}

	@PostMapping("/hello")
	public String hello(Authentication authentication) {
		System.out.println("hello");

		return "Hello!";
	}

	@PostMapping("/ciao")
	public String ciao(Authentication authentication) {
		System.out.println("ciao");

		return "Ciao!";
	}
}
