package com.example.barservice;

import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

@Service
public class RetryableBarService {

    private BarService barService;
    private RetryTemplate retryTemplate = RetryTemplate.builder()
            .maxAttempts(10)
            .fixedBackoff(1000)
            .retryOn(CachedBarNotFoundException.class).build();

    public RetryableBarService(BarService barService) {
        this.barService = barService;
    }

    public String getBar(String id)  {
        return retryTemplate.execute(ctx -> barService.getBar(id));
    }
}
