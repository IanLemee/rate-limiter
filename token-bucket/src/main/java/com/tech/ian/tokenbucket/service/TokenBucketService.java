package com.tech.ian.tokenbucket.service;

import com.tech.ian.tokenbucket.service.ex.RateLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TokenBucketService {

    private final Map<String, List<Integer>> map = new ConcurrentHashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenBucketService.class);

    public void processRequest(String name) {
        if (map.containsKey(name)) {
            List<Integer> integers = map.get(name);
            if (integers.isEmpty()) {
                LOGGER.warn("bucket for user: {} is empty, throwing an exception", name);
                throw new RateLimitExceededException();
            }
            integers.removeLast();
            LOGGER.info("removing token from bucket for user: {}, tokens remaining {}", name, integers.size());
            return;
        }

        map.put(name, new ArrayList<>(List.of(1,1,1,1,1)));
        LOGGER.info("creating token bucket for user: {}", name);

        List<Integer> integers = map.get(name);
        integers.removeLast();
        LOGGER.info("removing token from bucket for user: {}, tokens remaining {}", name, integers.size());
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void refillBucket() {
        Collection<List<Integer>> values = map.values();
        values.stream().filter(integers -> integers.size() < 5).forEach(integers -> integers.add(1));
    }
}
