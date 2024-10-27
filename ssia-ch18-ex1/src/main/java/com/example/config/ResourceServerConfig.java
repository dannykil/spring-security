package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${claim.aud}")
    private String claimAud;

    @Value("${jwkSetUri}")
    private String urlJwk;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore());
        resources.resourceId(claimAud);
//        resources.expressionHandler(handler());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwkTokenStore(urlJwk);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.DELETE, "/**").hasAuthority("fitnessadmin")
                .anyRequest().authenticated();
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

//    @Bean
//    public SecurityExpressionHandler<FilterInvocation> handler() {
//        return new OAuth2WebSecurityExpressionHandler();
//    }

}

// ex1) using KeyCloak
//curl -XPOST "http://localhost:8080/realms/master/protocol/openid-connect/token" -H "Content-Type: application/x-www-form-urlencoded" --data-urlencode "grant_type=password" --data-urlencode "username=rachel" --data-urlencode "password=12345" --data-urlencode "scope=fitnessapp" --data-urlencode "client_id=fitnessapp"