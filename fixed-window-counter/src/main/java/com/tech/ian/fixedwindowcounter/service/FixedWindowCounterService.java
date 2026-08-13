package com.tech.ian.fixedwindowcounter.service;

import com.tech.ian.exceptions.RateLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FixedWindowCounterService {
    private final Map<String, Integer> counterMap = new ConcurrentHashMap<>();
    private static final int COUNTER_THRESHOLD = 5;

    private static final Logger LOGGER = LoggerFactory.getLogger(FixedWindowCounterService.class);

    public void process(String name) {
        if (counterMap.containsKey(name)) {
            Integer counter = counterMap.get(name);
            if (counter >= COUNTER_THRESHOLD) {
                throw new RateLimitExceededException();
            }
            LOGGER.info("processing request");
            counterMap.put(name, counterMap.getOrDefault(name, counter) +1);
            counter = counterMap.get(name);
            LOGGER.info("Current user counter: {}", counter);
            return;
        }
        LOGGER.info("processing request");
        counterMap.put(name, 1);
        LOGGER.info("Current user counter: 1");
    }

    @SuppressWarnings("unused")
    @Scheduled(cron = "*/10 * * * * *")
    public void resetCounter() {
        counterMap.replaceAll((s, integer) -> 0);
        LOGGER.info("resetting counters");
    }
}
