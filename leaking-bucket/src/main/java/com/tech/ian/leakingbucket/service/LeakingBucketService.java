package com.tech.ian.leakingbucket.service;

import com.tech.ian.exceptions.RateLimitExceededException;
import com.tech.ian.leakingbucket.dto.RequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class LeakingBucketService {
    private static final int QUEUE_SIZE_THRESHOLD = 5;
    private static final int OUTFLOW_RATE = 2;
    private final BlockingQueue<RequestDto> queue = new LinkedBlockingQueue<>(QUEUE_SIZE_THRESHOLD);

    public void addRequest(RequestDto request) {
        if (!queue.offer(request)) {
            throw new RateLimitExceededException();
        }
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void processQueue() {
        for (int i = 0; i < OUTFLOW_RATE; i++) {
            RequestDto poll = queue.poll();
            if (poll == null) break;
            // process request
        }
    }
}
