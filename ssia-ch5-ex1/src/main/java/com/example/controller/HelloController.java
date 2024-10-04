package com.example.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@RestController
@Controller
public class HelloController {

	@GetMapping("/hello")
//	public String hello() { // 2024-10-04
	public String hello(Authentication authentication) {
//		2024-10-04
//		스프링은 인증을 메서드 매개변수에 곧바로 주입할 수 있으므로 엔드포인트 수준에서는 더 편하게 컨텍스트에서 인증을 얻을 수 있다
//		SecurityContext context = SecurityContextHolder.getContext();
//		Authentication authentication = context.getAuthentication();

//		2024-09-28
//		1. 스프링 시큐리티의 기본 구성 및 확인
//		curl http://localhost:8080/hello
//		curl -u user:b2f8f79d-cc80-43a3-93e5-32a70b815c16 http://localhost:8080/hello
//		포스트맨으로 테스트하면 401 unauthorization이 발생하는 것을 확인할 수 있음
//		curl은 <username>:<password> 문자열을 base64로 인코딩하고 결과를 접두사 Basic이 붙은 Authorization 헤더의 값으로 보낸다
//		1) echo -n user:529bc93f-9ba2-4d7d-abef-c5bb53821639 | base64
//		2) dXNlcjpiMmY4Zjc5ZC1jYzgwLTQzYTMtOTNlNS0zMmE3MGI4MTVjMTY=
//		3) curl -H "Authorization: Basic dXNlcjpiMmY4Zjc5ZC1jYzgwLTQzYTMtOTNlNS0zMmE3MGI4MTVjMTY=" http://localhost:8080/hello

//		2. HTTPS 통신
//		1) 인증서는 구매 및 갱신해서 사용해야하기 때문에 테스트를 위해서는 openssl을 통해 자체 생성(12345, KO)
//		- openssl req -newkey rsa:2048 -x509 -keyout key.pem -out cert.pem -days 365
//		* key.pem  : private key
//		* cert.pem : public key
//		2) 위 두 파일은 HTTPS 활성화를 위한 자체 서명 인증서를 생성하는데 필요(12345)
//		openssl pkcs12 -export -in cert.pem -inkey key.pem -out certificate.p12 -name "certificate"
//		* pkcs12 형식 : Public Key Cryptography Standard #12
//		curl -u user:3eb487fc-7cbd-4f8f-b7a1-da900d9ad358 -k https://localhost:8080/hello
//		return "Hello!";
		return "Hello, " + authentication.getName() + "!!!"; // 2024-10-04
	}

	@GetMapping("/bye")
	@Async
	public void goodbye() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		String username = securityContext.getAuthentication().getName();

		System.out.println("securityContext : " + securityContext);
		System.out.println("username : " + username);
	}

	@GetMapping("/ciao")
	public String ciao() throws Exception {
		Callable<String> task = () -> {
			SecurityContext securityContext = SecurityContextHolder.getContext();
//			String username = securityContext.getAuthentication().getName();
//
//			System.out.println("securityContext : " + securityContext);
//			System.out.println("username : " + username);
			return securityContext.getAuthentication().getName();
		};

		ExecutorService e = Executors.newCachedThreadPool();
		try {
			var contextTask = new DelegatingSecurityContextCallable<>(task);
			return "Ciao, " + e.submit(contextTask).get() + "!!!";
//			return "Ciao, " + e.submit(task).get() + "!!!";
		}
		finally {
			e.shutdown();
		}
	}

	@GetMapping("/hola")
	public String hola() throws Exception {
		Callable<String> task = () -> {
			SecurityContext context = SecurityContextHolder.getContext();
			return context.getAuthentication().getName();
		};

		ExecutorService e = Executors.newCachedThreadPool();
		e = new DelegatingSecurityContextExecutorService(e);
		try {
			return "Hola, " + e.submit(task).get() + "!";
		} finally {
			e.shutdown();
		}
	}

	@GetMapping("/home")
	public String home() {
		return "home.html";
	}

}
