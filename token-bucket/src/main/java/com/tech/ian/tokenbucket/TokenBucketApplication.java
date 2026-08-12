package com.tech.ian.tokenbucket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TokenBucketApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenBucketApplication.class, args);
    }

}
