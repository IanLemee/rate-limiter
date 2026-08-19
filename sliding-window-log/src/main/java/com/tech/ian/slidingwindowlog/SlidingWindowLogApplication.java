package com.tech.ian.slidingwindowlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SlidingWindowLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlidingWindowLogApplication.class, args);
    }

}
