package com.tech.ian.slidingwindowcounter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SlidingWindowCounterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlidingWindowCounterApplication.class, args);
    }

}
