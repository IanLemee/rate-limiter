package com.tech.ian.fixedwindowcounter.service;

import com.tech.ian.exceptions.RateLimitExceededException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FixedWindowCounterService {
    private final Map<String, AtomicInteger> counterMap = new ConcurrentHashMap<>();
    private static final int COUNTER_THRESHOLD = 5;

    public void process(String name) {
        AtomicInteger counter = counterMap.computeIfAbsent(name, k -> new AtomicInteger(0));

        if (counter.incrementAndGet() > COUNTER_THRESHOLD) {
            counter.decrementAndGet();
            throw new RateLimitExceededException();
        }
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void resetCounter() {
        counterMap.clear();
    }
}
