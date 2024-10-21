package com.example.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.List;

//ssia-ch15-ex1
//@Configuration
//@EnableAuthorizationServer
//public class AuthServerConfig
//        extends AuthorizationServerConfigurerAdapter {
//
//    @Value("${jwt.key}")
//    private String jwtKey;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("client")
//                .secret("secret")
//                .authorizedGrantTypes("password", "refresh_token")
//                .scopes("read");
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//        endpoints
//                .authenticationManager(authenticationManager)
//                .tokenStore(tokenStore())
//                .accessTokenConverter(jwtAccessTokenConverter());
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
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) {
//        security.checkTokenAccess("isAuthenticated()"); // check_token 엔드포인트를 호출할 수 있는 조건 지정
//    }
//}

//ssia-ch15-ex2
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig
        extends AuthorizationServerConfigurerAdapter {

    @Value("${password}")
    private String password;

    @Value("${privateKey}")
    private String privateKey;

    @Value("${alias}")
    private String alias;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret("secret")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read")
                .and()
                .withClient("resourceserver")
                .secret("resourceserversecret");
    }

    // ssia-ch15-ex1~3
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//        endpoints
//                .authenticationManager(authenticationManager)
//                .tokenStore(tokenStore())
//                .accessTokenConverter(jwtAccessTokenConverter());
//    }

    // ssia-ch15-ex4
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();

        var tokenEnhancers =
                List.of(new CustomTokenEnhancer(),
                        jwtAccessTokenConverter());

        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        var converter = new JwtAccessTokenConverter();

        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(
                        new ClassPathResource(privateKey),
                        password.toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(alias));

        return converter;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .checkTokenAccess("isAuthenticated()") // /oauth/check_token : 엔드포인트를 호출할 수 있는 조건 지정
                .tokenKeyAccess("isAuthenticated()"); //  /oauth/token_key   : 공개키를 제공하는 엔드포인트 구성
    }
}

//ssia-ch15-ex1
// 1. grant_type : password
// 1) 액세스 토큰 생성
// curl -v -XPOST 'http://localhost:8080/oauth/token' -u 'client:secret' -d 'grant_type=password' -d 'username=john' -d 'password=12345' -d 'scope=read'
// 2) 액세스 토큰 세부정보 확인
// curl -v -XPOST 'http://localhost:8080/oauth/check_token' -u 'client:secret' -d 'token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3Mjk0Njk0NDcsInVzZXJfbmFtZSI6ImpvaG4iLCJhdXRob3JpdGllcyI6WyJyZWFkIl0sImp0aSI6ImQ2NTJlM2Y4LTM5YWUtNGZmNi1iNDYzLTdlNmI5ZDUwZDM1OSIsImNsaWVudF9pZCI6ImNsaWVudCIsInNjb3BlIjpbInJlYWQiXX0.Jt4xaFH7bUkUozRLKUSY9-PiChw-SZCZetbSv0qmqZs'
// 3) 리소스 서버 엔드포인트 호출
// curl -H "Authorization: bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3Mjk0Njk0NDcsInVzZXJfbmFtZSI6ImpvaG4iLCJhdXRob3JpdGllcyI6WyJyZWFkIl0sImp0aSI6ImQ2NTJlM2Y4LTM5YWUtNGZmNi1iNDYzLTdlNmI5ZDUwZDM1OSIsImNsaWVudF9pZCI6ImNsaWVudCIsInNjb3BlIjpbInJlYWQiXX0.Jt4xaFH7bUkUozRLKUSY9-PiChw-SZCZetbSv0qmqZs" "http://localhost:9090/hello"

//ssia-ch15-ex2
// 1. grant_type : password
// 1) 액세스 토큰 생성
// curl -v -XPOST 'http://localhost:8080/oauth/token' -u 'client:secret' -d 'grant_type=password' -d 'username=john' -d 'password=12345' -d 'scope=read'
// 2) 액세스 토큰 세부정보 확인
// curl -v -XPOST 'http://localhost:8080/oauth/check_token' -u 'client:secret' -d 'token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3Mjk0NzE1MjIsInVzZXJfbmFtZSI6ImpvaG4iLCJhdXRob3JpdGllcyI6WyJyZWFkIl0sImp0aSI6IjRjYmFiM2RkLTdkYmMtNDdlOC05N2I1LWQ2MDM2M2I0N2E2YiIsImNsaWVudF9pZCI6ImNsaWVudCIsInNjb3BlIjpbInJlYWQiXX0.UnGvhTdvXFSk0t4dbH8a8McHnRPF8LDr4fbhBfnH_T7HKWJ4s-5q0Glu-Bouf6FMIiJshslOuDPq9Mm41ADU5opgoDEahus5HRjuEI-RQ0FGIHC_H9PHfugfIoEzKc1SEG4wcmzchvS2Z6Fuiz0lCOCyg0igxHHPhvxX_28fYc9uM8FKW_L_NilFkzTeEVuv2OOcdakJ3Sz3cWL7I3FjNk3iOXqTTsSwanbAxP43uFPc6LWw9AgP4Vg-u5zMTy_VI9BzJtckNWosl2O134z5TKed-kCm8LPtPF8sjwma0rS4CjcswbJ418af6EeEefIXEaRsK6dCA2EcIBHxv7EMFw'
// 3) 리소스 서버 엔드포인트 호출
// curl -H "Authorization: bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqb2huIiwic2NvcGUiOlsicmVhZCJdLCJnZW5lcmF0ZWRJblpvbmUiOiJBc2lhL1Nlb3VsIiwiZXhwIjoxNzI5NDc1NDQ5LCJhdXRob3JpdGllcyI6WyJyZWFkIl0sImp0aSI6ImE4YmJlZDdmLThiZDYtNGU2OC05OTE4LTQ4MDI2YTQ1Yjc1ZiIsImNsaWVudF9pZCI6ImNsaWVudCJ9.nqOlaSjUFawzBb4rAknufdw0g2hgJRUEwx9C3VKj5vObUU7tiLVoP61FVvPXSIMjXJuLjjUR1ZZFeF6WZDaG2bu-cjoI5pfcU_K4IGntM8C5DS6w1v-F0jhZ8amXPUb-wGHpFqWRCDfItZoQFd8NWMasjhPPVLPS3JxlheBUmj2Kt6-bHRLtYNuCz2etTbe-3ogeBO-q8mhi9uiNFKHROSOVzPOOOcBReXQvti79aEItDcLY3htR4sLw-qt1lsCMNF6aC2k12Io7YJ-oOquc0D7OwUb_NfKjlsnVjTim2woFiBwfT9LhxN4xPON6aoZdJrgg1Jya9fwkKMU5D5C8LQ","token_type":"bearer" "http://localhost:9090/hello"

//ssia-ch15-ex3
// 1) 공개키 확인
// curl -v XGET -u resourceserver:resourceserversecret http://localhost:8080/oauth/token_key