package com.kd.example.retry.controllers;

import com.kd.example.retry.services.RetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
