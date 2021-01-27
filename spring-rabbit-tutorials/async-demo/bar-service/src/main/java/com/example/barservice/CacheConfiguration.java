package com.example.barservice;

import com.hazelcast.config.Config;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    @Profile("default")
    public Config config() {
        return new Config().setClusterName("local");
    }
}
