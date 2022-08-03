package com.ntozic.airsoft.iam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/user")
    public ResponseEntity<String> helloUser() {
        return ResponseEntity.ok("GET /test/user");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> helloAdmin() {
        return ResponseEntity.ok("GET /test/admin");
    }
}
