package com.kd.example.retry.controllers;

import com.kd.example.retry.services.RetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author kuldeep
 */
@RestController
@RequestMapping()
@RequiredArgsConstructor
public class RetryController {
    private final RetryService retryService;

    @GetMapping("retry")
    public ResponseEntity<?> retryExample() {
        return ResponseEntity.ok(retryService.callExternalService());
    }

    @GetMapping("retry/recover")
    public ResponseEntity<?> retryExampleRecover() {
        return ResponseEntity.ok(retryService.callExternalServiceWithCallback());
    }

    @GetMapping("retry/webflux")
    public Mono<String> retryExampleWithWebflux() {
        return retryService.callExternalServiceWithWebFlux();
    }

    @GetMapping("retry/webflux/recover")
    public Mono<String> retryExampleWithWebfluxRecover() {
        return retryService.callExternalServiceWithWebFluxWthCallback();
    }
}
