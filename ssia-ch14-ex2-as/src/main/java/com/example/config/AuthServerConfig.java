package com.example.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig
        extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret("secret")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read");
    }

    // ch14-ex2 : DB에 저장하는 방식 >>> 서버 재구동해도 토큰은 유지됨
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    // ch14-ex1 메모리에 저장하는 방식 >>> 서버 재구동 시 토큰 재발행 해야함
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//        System.out.println("endpoints : " + endpoints);
//
//        endpoints.authenticationManager(authenticationManager);
//    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("isAuthenticated()"); // check_token 엔드포인트를 호출할 수 있는 조건 지정
    }
}

// ch14-ex2
// 1. grant_type : password
// 1) 액세스 토큰 생성
// curl -v -XPOST 'http://localhost:8080/oauth/token' -u 'client:secret' -d 'grant_type=password' -d 'username=john' -d 'password=12345' -d 'scope=read'
// 2) 액세스 토큰 세부정보 확인
// curl -v -XPOST 'http://localhost:8080/oauth/check_token' -u 'client:secret' -d 'token=c9a061c8-268b-4f5f-b8ed-3207701772a6'
// 3) 리소스 서버 엔드포인트 호출
// curl -H "Authorization: bearer c9a061c8-268b-4f5f-b8ed-3207701772a6" "http://localhost:9090/hello"