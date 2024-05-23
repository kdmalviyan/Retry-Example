package com.kd.example.retry.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author kuldeep
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RetryService {
    private final RestTemplate restTemplate;
    @Retryable(
            value = { RuntimeException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public String callExternalService() {
        log.info("Calling external service...");
        return restTemplate.getForObject("http://retry.example.com/api/resource", String.class);
    }
}
