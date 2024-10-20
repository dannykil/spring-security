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

    // ch13-ex1-1
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

    // ch13-ex1-2
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        System.out.println("clients : " + clients);
//        clients.inMemory()
//                .withClient("client")
//                .secret("secret")
//                .authorizedGrantTypes("password", "refresh_token")
//                .scopes("read");
//    }

    // ch13-ex2-1
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
                .scopes("info")
                .and()

                // ch14-ex1
                // 리소스 서버 자격 증명 추가
                .withClient("resourceserver")
                .secret("resourceserversecret");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        System.out.println("endpoints : " + endpoints);

        endpoints.authenticationManager(authenticationManager);
    }

    // ch14-ex1
    // 리소스 서버는 ch14-ex1
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("isAuthenticated()"); // check_token 엔드포인트를 호출할 수 있는 조건 지정
    }
}

// ch13-ex1
// 1. grant_type : password
// 1) 교재내용(InvalidGrantException)
// curl -v -XPOST -u client:secret http://localhost:8080/oauth/token?grant_type=password&username=john&password=12345&scope=read
// >>> 교재에서 소개해주는 방식은 POST 방식에 맞지 않음
// 2) 수정사항v
// curl -v -XPOST 'http://localhost:8080/oauth/token' -u 'client:secret' -d 'grant_type=password' -d 'username=john' -d 'password=12345' -d 'scope=read'
// >>> https://stackoverflow.com/questions/36548838/spring-security-oauth2-invalidgrantexception
// curl -v -XPOST 'http://localhost:8080/oauth/token' -u 'client2:secret2' -d 'grant_type=password' -d 'username=john' -d 'password=12345' -d 'scope=read'

// ch13-ex1
// 2. grant_type : authorization_code
// 1) 승인코드 생성
// http://localhost:8080/oauth/authorize?response_type=code&client_id=client2&scope=read
// 2) redirect_uri로 이동
// http://localhost:9090/home?code=0NHFNI
// 3)
// curl -v -XPOST 'http://localhost:8080/oauth/token' -u 'client2:secret2' -d 'grant_type=authorization_code' -d 'scope=read' -d 'code=0NHFNI'
// * 승인코드는 1회만 사용가능

// ch13-ex1
// 3. grant_type : client_credentials
// curl -v -XPOST 'http://localhost:8080/oauth/token' -u 'client3:secret3' -d 'grant_type=client_credentials' -d 'scope=info'


// ch14-ex1
// 1. grant_type : password
// 1) 액세스 토큰 생성
// curl -v -XPOST 'http://localhost:8080/oauth/token' -u 'client2:secret2' -d 'grant_type=password' -d 'username=john' -d 'password=12345' -d 'scope=read'
// 2) 액세스 토큰 세부정보 확인
// curl -v -XPOST 'http://localhost:8080/oauth/check_token' -u 'resourceserver:resourceserversecret' -d 'token=01b04a75-b1f5-4788-a226-71df046905e0'
// 3) 리소스 서버 엔드포인트 호출
// curl -H "Authorization: bearer 01b04a75-b1f5-4788-a226-71df046905e0" "http://localhost:9090/hello"
// curl -v "http://localhost:9090/hello"(error)