package com.tech.ian.slidingwindowcounter.service;

import com.tech.ian.exceptions.RateLimitExceededException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SlidingWindowCounterService {
    private final Map<String, AtomicInteger> previousWindow = new ConcurrentHashMap<>();
    private final Map<String, AtomicInteger> currentWindow = new ConcurrentHashMap<>();
    private static final int THRESHOLD = 7;

    public void process(String name) {
        int current = currentWindow.computeIfAbsent(name, k -> new AtomicInteger(0)).get();
        int previous = previousWindow.getOrDefault(name, new AtomicInteger(0)).get();

        int currentSecond = LocalTime.now().getSecond();
        double previousWeight = (60.0 - currentSecond) / 60.0;

        int estimatedRequests = (int) (current + (previous * previousWeight));

        if (estimatedRequests >= THRESHOLD) {
            throw new RateLimitExceededException();
        }
        currentWindow.get(name).incrementAndGet();
    }

    @Scheduled(cron = "0 * * * * *")
    public void refreshWindow() {
        previousWindow.clear();
        previousWindow.putAll(currentWindow);
        currentWindow.clear();
    }
}
