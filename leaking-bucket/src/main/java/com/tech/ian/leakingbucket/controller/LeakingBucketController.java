package com.tech.ian.leakingbucket.controller;

import com.tech.ian.leakingbucket.dto.RequestDto;
import com.tech.ian.leakingbucket.service.LeakingBucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeakingBucketController {
    private final LeakingBucketService leakingBucketService;

    public LeakingBucketController(LeakingBucketService leakingBucketService) {
        this.leakingBucketService = leakingBucketService;
    }

    @PostMapping("process")
    public ResponseEntity<Void> request(@RequestBody RequestDto request) {
        leakingBucketService.addRequest(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
