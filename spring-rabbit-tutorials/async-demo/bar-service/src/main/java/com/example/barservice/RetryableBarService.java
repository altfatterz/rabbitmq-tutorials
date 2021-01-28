package com.example.barservice;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class RetryableBarService {

    private BarService barService;

    public RetryableBarService(BarService barService) {
        this.barService = barService;
    }

    @Retryable(value = CachedBarNotFoundException.class, maxAttempts = 10, backoff = @Backoff(500))
    public String getBar(String id)  {
        return barService.getBar(id);
    }

    // Will be executed after we tried the maxAttempts
    @Recover
    public String recover(CachedBarNotFoundException e) {
        return null;
    }
}
