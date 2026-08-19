package com.tech.ian.slidingwindowcounter.controller;

import com.tech.ian.slidingwindowcounter.service.SlidingWindowCounterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlidingWindowCounterController {
    private final SlidingWindowCounterService slidingWindowCounterService;

    public SlidingWindowCounterController(SlidingWindowCounterService slidingWindowCounterService) {
        this.slidingWindowCounterService = slidingWindowCounterService;
    }

    @PostMapping("process")
    public ResponseEntity<Void> request() {
        slidingWindowCounterService.process();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("")
    public ResponseEntity<Void> refresh() {
        slidingWindowCounterService.refreshWindow();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
