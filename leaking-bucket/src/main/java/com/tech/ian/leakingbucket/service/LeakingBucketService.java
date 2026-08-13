package com.tech.ian.leakingbucket.service;

import com.tech.ian.exceptions.RateLimitExceededException;
import com.tech.ian.leakingbucket.dto.RequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class LeakingBucketService {
    private final Queue<RequestDto> queue = new ConcurrentLinkedQueue<>();
    private static final int QUEUE_SIZE_THRESHOLD = 5;
    private static final int OUTFLOW_RATE = 2;

    private static final Logger LOGGER = LoggerFactory.getLogger(LeakingBucketService.class);

    public void addRequest(RequestDto request) {
        if (queue.size() >= QUEUE_SIZE_THRESHOLD) {
            LOGGER.warn("Queue size: {} is equal or greater than threshold: {}", queue.size(), QUEUE_SIZE_THRESHOLD);
            throw new RateLimitExceededException();
        }
        queue.add(request);
        LOGGER.info("Adding request at queue. Queue size: {}", queue.size());
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void processQueue() {
            for (int i = 0; i <  OUTFLOW_RATE && !queue.isEmpty(); i++) {
                RequestDto poll = queue.poll();
                LOGGER.info("processing request data: {}", poll);
                LOGGER.info("Queue size: {}", queue.size());
            }
    }
}
