package com.example.pulsar_backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/check")
    public String healthCheck() {
        return "Hello from Spring Boot backend!";
    }
}
