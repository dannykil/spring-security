package com.example.config;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

// ssia-ch15-ex4
// 토큰에 있는 추가 세부 정보도 고려하도록 JwtAccessTokenConverter의 맞춤 구현
public class AdditionalClaimsAccessTokenConverter
        extends JwtAccessTokenConverter {

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        var authentication = super.extractAuthentication(map); // extractAuthentication() 메서드 재정의
        authentication.setDetails(map);
        return authentication;
    }
}