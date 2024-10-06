package com.example.csrf;

import com.example.entities.Token;
import com.example.repository.JpaTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Component // 강의자료에는 없음
public class CustomCsrfTokenRepository
        implements CsrfTokenRepository {

    @Autowired
    private JpaTokenRepository jpaTokenRepository;

    @Override
    public CsrfToken generateToken(HttpServletRequest httpServletRequest) {
        System.out.println("### generateToken");

        String uuid = UUID.randomUUID().toString();
        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
    }

    @Override
    public void saveToken(CsrfToken csrfToken, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        System.out.println("### saveToken");

        String identifier = httpServletRequest.getHeader("X-IDENTIFIER");
        Optional<Token> existingToken = jpaTokenRepository.findTokenByIdentifier(identifier);

        System.out.println("existingToken.isPresent() : " + existingToken.isPresent());
        if (existingToken.isPresent()) {
            Token token = existingToken.get();
            System.out.println("token : " + token.getToken());
            token.setToken(csrfToken.getToken());
        } else {
            Token token = new Token();
            token.setToken(csrfToken.getToken());
            token.setIdentifier(identifier);
            jpaTokenRepository.save(token);
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest httpServletRequest) {
        System.out.println("### loadToken");

        String identifier = httpServletRequest.getHeader("X-IDENTIFIER");
        System.out.println("httpServletRequest.getHeader(\"X-IDENTIFIER\") : " + httpServletRequest.getHeader("X-IDENTIFIER"));
        System.out.println("httpServletRequest.getHeader(\"X-CSRF-TOKEN\") : " + httpServletRequest.getHeader("X-CSRF-TOKEN"));

        Optional<Token> existingToken = jpaTokenRepository.findTokenByIdentifier(identifier);

        System.out.println("existingToken.isPresent() : " + existingToken.isPresent());
        if (existingToken.isPresent()) {
            Token token = existingToken.get();
            System.out.println("token : " + token.getToken());
            return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token.getToken());
        }

        return null;
    }
}

//curl -X POST -H "X-IDENTIFIER:12345" -H "X-CSRF-TOKEN:ba5bc6a2-a194-4c90-859b-5af1d9f90342" http://localhost:8080/hello