package com.example.barservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class BarServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarServiceApplication.class, args);
    }

}
