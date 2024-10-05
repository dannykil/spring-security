package com.example.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/hello")
	public String hello(Authentication authentication) {

		return "Hello!";
	}

	@GetMapping("/ciao")
	public String ciao(Authentication authentication) {

		return "Ciao!";
	}

	@GetMapping("/hola")
	public String hola(Authentication authentication) {

		return "Hola!";
	}

	@GetMapping("/a")
	public String GetEndpoint_a(Authentication authentication) {

		return "Works!_GetEndpoint_a";
	}

	@PostMapping("/a")
	public String PostEndpoint_a(Authentication authentication) {

		return "Works!_PostEndpoint_a";
	}

	@GetMapping("/a/b")
	public String GetEndpoint_ab(Authentication authentication) {

		return "Works!_GetEndpoint_ab";
	}

	@GetMapping("/a/b/c")
	public String GetEndpoint_abc(Authentication authentication) {

		return "Works!_GetEndpoint_abc";
	}

	@GetMapping("/product/{code}")
	public String product(@PathVariable String code) {

		return code;
	}

	@GetMapping("/video/{country}/{language}")
	public String video(@PathVariable String country, @PathVariable String language) {

		return "Video allowed for " + country + " " + language;
	}

//	@GetMapping("/email/{email}")
//	public String video(@PathVariable String email) {
//
//		return "Allowed for email " + email;
//	}
}
