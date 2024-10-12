package com.example.authentication.filters;

import com.example.authentication.OtpAuthentication;
import com.example.authentication.UsernamePasswordAuthentication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class InitialAuthenticationFilter extends OncePerRequestFilter {

    Logger logger = Logger.getLogger(InitialAuthenticationFilter.class.getName());

    @Autowired
    private AuthenticationManager manager;

    @Value("${jwt.signing.key}")
    private String signingKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("\n ### InitialAuthenticationFilter > doFilterInternal");

        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String code = request.getHeader("code");
        System.out.println("username : " + username);
        System.out.println("password : " + password);
        System.out.println("code : " + code);

        if (code == null) {
            Authentication a = new UsernamePasswordAuthentication(username, password);
            manager.authenticate(a); // AuthenticationManager가 Provider에게 인증논리 위임
        } else {
            Authentication a = new OtpAuthentication(username, code);
            SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));

            String jwt = Jwts.builder()
                    .setClaims(Map.of("username", username))
                    .signWith(key)
                    .compact();

            System.out.println("jwt : " + jwt);
//            response.setHeader("Authorization", jwt);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        System.out.println("\n ### InitialAuthenticationFilter > shouldNotFilter");

        return !request.getServletPath().equals("/login");
    }
}

// curl -H "username:dannielle" -H "password:12345" http://localhost:9090/login
// curl -v -H "username:dannielle" -H "code:1480" http://localhost:9090/login
// curl -v -H "username:dannielle" -H "code:1480" http://localhost:9090/test
// curl -H "Authorization:eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImRhbm5pZWxsZSJ9.K2fQNSciNg6I2AO1zN490WPjh_b4Igd2KnvHME-iLgU" http://localhost:9090/test