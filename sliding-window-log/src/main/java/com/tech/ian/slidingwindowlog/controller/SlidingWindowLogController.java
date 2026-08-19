package com.tech.ian.slidingwindowlog.controller;

import com.tech.ian.slidingwindowlog.service.SlidingWindowLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlidingWindowLogController {

    private final SlidingWindowLogService slidingWindowLogService;

    public SlidingWindowLogController(SlidingWindowLogService slidingWindowLogService) {
        this.slidingWindowLogService = slidingWindowLogService;
    }

    @PostMapping("process")
    public ResponseEntity<Void> request() {
        slidingWindowLogService.process();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
