package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

@Controller
public class HelloController {

	private Logger logger =
			Logger.getLogger(HelloController.class.getName());

	@GetMapping("/")
	public String main() {
		return "main.html";
	}

	@PostMapping("/test")
	@ResponseBody
//    @CrossOrigin("http://localhost:8080") // CORS 정책 적용방법1
	public String test() {
		logger.info("Test method called");
		return "HELLO";
	}
}
