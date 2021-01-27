package com.example.barservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BarService {

    private final Logger logger = LoggerFactory.getLogger(BarService.class);

    @Cacheable("bar")
    public String bar(String id) {
        logger.info("Not from the cache, bar(" + id + ") executed ");
        return "todo";
    }
}
