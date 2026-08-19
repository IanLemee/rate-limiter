package com.tech.ian.slidingwindowlog.service;

import com.tech.ian.exceptions.RateLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SlidingWindowLogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlidingWindowLogService.class);

    private final Map<String, Queue<Long>> userLogs = new ConcurrentHashMap<>();
    private static final int ALLOWED_REQUESTS = 2;
    private static final long WINDOW_IN_MS = 60000;

    public void process(String name) {
        long currentTime = System.currentTimeMillis();
        Queue<Long> log = userLogs.computeIfAbsent(name, k -> new ConcurrentLinkedQueue<>());

        // Remove old requests (sliding the window)
        while (!log.isEmpty() && currentTime - log.peek() > WINDOW_IN_MS) {
            log.poll();
        }

        if (log.size() >= ALLOWED_REQUESTS) {
            throw new RateLimitExceededException();
        }

        log.add(currentTime);
    }

}
