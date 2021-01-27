package com.example.barservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class MessageConsumer {

    private final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    private BarService barService;

    public MessageConsumer(BarService barService) {
        this.barService = barService;
    }

    @Bean
    public Consumer<Work> work() {
        return work -> {
            simulateSlowService(); // delay 1 when the consumer is not picking up the work
            logger.info("Handling payload {}", work);
            barService.calculateBar(work.getId());
        };
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
