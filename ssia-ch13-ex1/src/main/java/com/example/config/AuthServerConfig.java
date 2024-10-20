package com.example.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;

import java.util.List;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig
        extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    // ex1-1
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        var service = new InMemoryClientDetailsService();
//
//        var cd = new BaseClientDetails();
//        cd.setClientId("client");
//        cd.setClientSecret("secret");
//        cd.setScope(List.of("read"));
//        cd.setAuthorizedGrantTypes(List.of("password"));
//
//        service.setClientDetailsStore(Map.of("client", cd));
//
//        clients.withClientDetails(service);
//    }

    // ex1-2
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        System.out.println("clients : " + clients);
//        clients.inMemory()
//                .withClient("client")
//                .secret("secret")
//                .authorizedGrantTypes("password", "refresh_token")
//                .scopes("read");
//    }

    // ex2-1
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        System.out.println("clients : " + clients);
        clients.inMemory()

                .withClient("client1")
                .secret("secret1")
                .authorizedGrantTypes("authorization_code")
                .scopes("read")
                .redirectUris("http://localhost:9090/home")
                .and()

                .withClient("client2")
                .secret("secret2")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .scopes("read")
                .redirectUris("http://localhost:9090/home")
                .and()

                .withClient("client3")
                .secret("secret3")
                .authorizedGrantTypes("client_credentials")
                .scopes("info");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        System.out.println("endpoints : " + endpoints);

        endpoints.authenticationManager(authenticationManager);
    }

//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) {
//        security.checkTokenAccess("isAuthenticated()");
//    }
}

// 1. grant_type : password
// 1) 교재내용(InvalidGrantException)
// curl -v -XPOST -u client:secret http://localhost:8080/oauth/token?grant_type=password&username=john&password=12345&scope=read
// >>> 교재에서 소개해주는 방식은 POST 방식에 맞지 않음

// 2) 수정사항v
// curl -v -XPOST 'http://localhost:8080/oauth/token' -u 'client:secret' -d 'grant_type=password' -d 'username=john' -d 'password=12345' -d 'scope=read'
// >>> https://stackoverflow.com/questions/36548838/spring-security-oauth2-invalidgrantexception
// curl -v -XPOST 'http://localhost:8080/oauth/token' -u 'client2:secret2' -d 'grant_type=password' -d 'username=john' -d 'password=12345' -d 'scope=read'


// 2. grant_type : authorization_code
// 1) 승인코드 생성
// http://localhost:8080/oauth/authorize?response_type=code&client_id=client2&scope=read
// 2) redirect_uri로 이동
// http://localhost:9090/home?code=0NHFNI
// 3)
// curl -v -XPOST 'http://localhost:8080/oauth/token' -u 'client2:secret2' -d 'grant_type=authorization_code' -d 'scope=read' -d 'code=0NHFNI'
// * 승인코드는 1회만 사용가능

// 3. grant_type : client_credentials
// curl -v -XPOST 'http://localhost:8080/oauth/token' -u 'client3:secret3' -d 'grant_type=client_credentials' -d 'scope=info'
