package com.example.barservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class MessageConsumer {

    private final Logger logger = LoggerFactory.getLogger(BarService.class);

    private BarService barService;

    public MessageConsumer(BarService barService) {
        this.barService = barService;
    }

    @Bean
    public Consumer<Work> work() {
        return work -> {
            logger.info("Handling payload {}", work);
            barService.bar(work.getId());
        };
    }
}
