package com.kd.example.retry.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author kuldeep
 */
@Configuration
public class BeansConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
