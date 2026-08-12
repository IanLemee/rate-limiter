package com.tech.ian.tokenbucket.controller;

import com.tech.ian.tokenbucket.service.TokenBucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class TokenBucketController {

    private final TokenBucketService service;

    public TokenBucketController(TokenBucketService service) {
        this.service = service;
    }

    @PostMapping("process")
    public ResponseEntity<Void> request(@RequestBody String name) {
        service.processRequest(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
