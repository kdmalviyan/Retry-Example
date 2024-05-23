package com.kd.example.retry.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

/**
 * @author kuldeep
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RetryService {
    private final RestTemplate restTemplate;
    private final WebClient webClient;
    @Retryable(
            value = { RuntimeException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public String callExternalService() {
        log.info("Calling external service...");
        return restTemplate.getForObject("http://retry.example.com/api/resource", String.class);
    }

    public Mono<String> callExternalServiceWithWebFlux() {
        return webClient.get()
                .uri("/api/resource")
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(
                        Retry.backoff(3, Duration.ofSeconds(2))
                                .maxBackoff(Duration.ofSeconds(10))
                                .filter(throwable -> throwable instanceof RuntimeException)
                                .doAfterRetry(retrySignal ->
                                        System.out.println("Retrying... attempt " + retrySignal.totalRetries())
                                )
                );
    }

    @Retryable(
            value = { RuntimeException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2),
            recover = "callback"
    )
    public String callExternalServiceWithCallback() {
        log.info("Calling external service...");
        return restTemplate.getForObject("http://retry.example.com/api/resource", String.class);
    }

    @Recover
    public String callback(RuntimeException e) {
        log.info("Callback called due to: " + e.getMessage());
        return "Fallback response";
    }

    public Mono<String> callExternalServiceWithWebFluxWthCallback() {
        return webClient.get()
                .uri("/api/resource")
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(
                        Retry.backoff(3, Duration.ofSeconds(2))
                                .maxBackoff(Duration.ofSeconds(10))
                                .filter(throwable -> throwable instanceof RuntimeException)
                                .doAfterRetry(retrySignal ->
                                        System.out.println("Retrying... attempt " + retrySignal.totalRetries())
                                )
                ).onErrorReturn("Fallback response");
    }
}
