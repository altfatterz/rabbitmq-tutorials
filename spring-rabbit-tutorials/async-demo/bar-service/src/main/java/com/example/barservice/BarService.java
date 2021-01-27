package com.example.barservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BarService {

    private final Logger logger = LoggerFactory.getLogger(BarService.class);

    @Cacheable("bar")
    public String getBar(String id) {
        logger.info("calculateBar for id:{}", id);
        return id + " !!!";
    }

    @CacheEvict("bar")
    public void deleteBar(String id) {
        logger.info("bar deleted with id:{}", id);
    }
}
