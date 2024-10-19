package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {


    // ClientRegistrationRepository 구현 1 : 스프링 컨텍스트에 추가
//    @Bean
//    public ClientRegistrationRepository clientRepository() {
//        var c = clientRegistration();
//        return new InMemoryClientRegistrationRepository(c); // ClientRegistration의 인스턴스를 메모리에 저장
//    }

    // 1) ClientRegistration 계약 구현 1
//    private ClientRegistration clientRegistration() {
//
//        ClientRegistration cr = ClientRegistration.withRegistrationId("github")
//                .clientId("Ov23liBOdZbqUgcz89Mi")
//                .clientSecret("c84e2a20363c1e24d407e3c97fcc37a0d83daf0b")
//                .scope(new String[]{"read:user"})
//                .authorizationUri("https://github.com/login/oauth/authorize")
//                .tokenUri("https://github.com/login/oauth/access_token")
//                .userInfoUri("https://api.github.com/user")
//                .userNameAttributeName("id")
//                .clientName("GitHub")
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUriTemplate("{baseUrl}/{action}/oauth2/code/{registrationId}")
//                .build();
//
//        return cr;
//    }

    // 2) ClientRegistration 계약 구현 2
//    private ClientRegistration clientRegistration() {
//        return CommonOAuth2Provider.GITHUB.getBuilder("github")
//                .clientId("Ov23liBOdZbqUgcz89Mi")
//                .clientSecret("c84e2a20363c1e24d407e3c97fcc37a0d83daf0b")
//                .build();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.oauth2Login(); // OAuth2LoginAuthenticationFilter를 필터 체인에 추가

        // ClientRegistrationRepository 구현 2 : oauth2Login 메서드의 Customizer 객체 사용(빈 사용하는 것과 혼용하지 말 것!)
//        http.oauth2Login(c -> {
//            c.clientRegistrationRepository(clientRepository());
//        });

        http.authorizeRequests()
                .anyRequest().authenticated();
    }
}

// 테스트 결과
// 1. 로그인(정상)
// 1.1) 깃헙 로그인
// 1.1.1) 깃헙 : 로그인 정상
// 1.1.2) 로컬 : 로그인 정상

// 1.2) 로컬 로그인
// 1.2.1) 깃헙 : 로그인 정상
// 1.2.2) 로컬 : 로그인 정상


// 2. 로그아웃(오류)
// 2.1) 깃헙에서 로그아웃
// 2.1.1) 깃헙 : 로그아웃 정상
// 2.1.2) 로컬 : 로그아웃 안됨
// * 하지만 /logout하면 됨

// 2.2) 로컬에서 로그아웃
// 2.2.1) 깃헙 : 로그아웃 안됨
// 2.2.2) 로컬 : 로그아웃 안됨
// * 로그아웃 총평
// 로그인처럼 로그아웃도 한 곳에서 하면 전체가 로그아웃 되어야 하는데 안되고 있음
// >>> 깃헙에서 제공하는 /logout 엔드포인트를 호출해야한다. 로컬이 아니라
