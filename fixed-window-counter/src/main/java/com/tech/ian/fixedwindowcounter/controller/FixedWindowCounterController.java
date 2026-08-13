package com.tech.ian.fixedwindowcounter.controller;

import com.tech.ian.fixedwindowcounter.service.FixedWindowCounterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FixedWindowCounterController {
    private final FixedWindowCounterService fixedWindowCounterService;

    public FixedWindowCounterController(FixedWindowCounterService fixedWindowCounterService) {
        this.fixedWindowCounterService = fixedWindowCounterService;
    }

    @PostMapping("process")
    public ResponseEntity<Void> request(@RequestBody String name) {
        fixedWindowCounterService.process(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
