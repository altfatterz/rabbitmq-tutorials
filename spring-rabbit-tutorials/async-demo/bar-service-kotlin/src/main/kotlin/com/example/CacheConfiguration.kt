package com.example

import com.hazelcast.config.Config
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@EnableCaching
class CacheConfiguration {

    @Bean
    @Profile("default")
    fun config(): Config {
        return Config().setClusterName("local")
    }
}