package com.dairy.take12.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class HealthController {
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}

