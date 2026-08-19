package com.tech.ian.tokenbucket.service;

import com.tech.ian.exceptions.RateLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TokenBucketService {

    private final Map<String, AtomicInteger> map = new ConcurrentHashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenBucketService.class);
    private static final int MAX_TOKENS = 5;
    public void processRequest(String name) {
        AtomicInteger tokens = map.computeIfAbsent(name, k -> new AtomicInteger(MAX_TOKENS));

        if (tokens.get() <= 0) {
            throw new RateLimitExceededException();
        }
        tokens.decrementAndGet();
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void refillBucket() {
        map.values().forEach(tokens -> {
            if (tokens.get() < MAX_TOKENS) {
                tokens.incrementAndGet();
            }
        });
    }
}
