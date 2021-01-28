package com.example.barservice;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class BarService {

    private final Logger logger = LoggerFactory.getLogger(BarService.class);

    private HazelcastInstance hazelcastInstance;

    public BarService(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    public void calculateBar(String id) {
        logger.info("calculateBar for id:{}", id);

        IMap<Object, Object> map = hazelcastInstance.getMap("bar");
        map.lock(id);
        map.put(id, "work with id " + id + " is done");

        try {
            simulateSlowService(); // delay 2 when the third party service is slow
        } finally {
            map.unlock(id);
        }
    }

    public String getBar(String id) throws CachedBarNotFoundException {
        IMap<Object, Object> map = hazelcastInstance.getMap("bar");
        String value = (String) map.get(id);
        if (value == null) throw new CachedBarNotFoundException("cached bar not found");
        return value;
    }

    public void cacheDetails() {
        IMap<Object, Object> map = hazelcastInstance.getMap("bar");
        map.forEach(new BiConsumer<Object, Object>() {
            @Override
            public void accept(Object o, Object o2) {
                logger.info("Key: {}, Value:{}", o, o2);
            }
        });
    }

    @CacheEvict("bar")
    public void deleteBar(String id) {
        logger.info("bar deleted with id:{}", id);
    }

    private void simulateSlowService() {
        try {
            long time = 5000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
