package com.kd.example.retry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class RetryExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetryExampleApplication.class, args);
	}

}
