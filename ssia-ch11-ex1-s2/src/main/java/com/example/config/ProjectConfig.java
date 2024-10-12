package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProjectConfig {

    // 여기의 목적은 무엇인가?(아직모름)
    @Bean
    public RestTemplate restTemplate() {
        System.out.println("\n ### ProjectConfig > RestTemplate");

        return new RestTemplate();
    }
}