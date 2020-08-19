package com.example.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(MessageProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) {
        logger.info("Sending message...");
        rabbitTemplate.convertAndSend(ProducerApplication.EXCHANGE_NAME,
                "foo.bar.baz", "Hello from RabbitMQ!");
    }
}
