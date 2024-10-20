package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//ssia-ch15-ex1
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Value("${jwt.key}")
//    private String jwtKey;
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.tokenStore(tokenStore());
//    }
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(jwtAccessTokenConverter());
//    }
//
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        var converter = new JwtAccessTokenConverter();
//        converter.setSigningKey(jwtKey);
//        return converter;
//    }
//}

//ssia-ch15-ex2
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Value("${publicKey}")
//    private String publicKey;
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.tokenStore(tokenStore());
//    }
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(jwtAccessTokenConverter());
//    }
//
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        var converter = new JwtAccessTokenConverter();
//        converter.setVerifierKey(publicKey);
//        return converter;
//    }
//}

//ssia-ch15-ex3
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//}

//ssia-ch15-ex4
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${publicKey}")
    private String publicKey;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        var converter = new AdditionalClaimsAccessTokenConverter();
        converter.setVerifierKey(publicKey);
        return converter;
    }
}