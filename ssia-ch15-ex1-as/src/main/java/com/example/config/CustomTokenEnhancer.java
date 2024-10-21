package com.example.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.time.ZoneId;
import java.util.Map;

// ssia-ch15-ex04
public class CustomTokenEnhancer implements TokenEnhancer {

    // 현재 토큰을 받고 향상된 토큰을 반환하는 enhance() 메서드 재정의
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken,
                                     OAuth2Authentication oAuth2Authentication) {

        var token = new DefaultOAuth2AccessToken(oAuth2AccessToken);

        Map<String, Object> info =
                Map.of("generatedInZone", ZoneId.systemDefault().toString());
        token.setAdditionalInformation(info);

        return token;
    }
}