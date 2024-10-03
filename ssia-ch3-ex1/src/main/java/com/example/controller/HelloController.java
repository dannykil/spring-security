package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/hello")
	public String hello() {

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
		return "Hello!";
	}
}
