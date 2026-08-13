package com.tech.ian.fixedwindowcounter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FixedWindowCounterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FixedWindowCounterApplication.class, args);
    }

}
