package com.smartclassroom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String securedEndpoint() {
        return "JWT is working! You are authenticated.";
    }
}
