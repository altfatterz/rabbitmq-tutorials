package com.example.barservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BarService {

    private final Logger logger = LoggerFactory.getLogger(BarService.class);

    @CachePut("bar")
    public String calculateBar(String id) {
        logger.info("calculateBar for id:{}", id);
        return id + " !!!";
    }

    @Cacheable("bar")
    public String getBar(String id) {
        logger.info("getBar for id:{}", id);
        return null;
    }

    @CacheEvict("bar")
    public void deleteBar(String id) {
        logger.info("bar deleted with id:{}", id);
    }
}
