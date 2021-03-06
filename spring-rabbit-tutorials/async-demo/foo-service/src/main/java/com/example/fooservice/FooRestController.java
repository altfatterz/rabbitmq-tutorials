package com.example.fooservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooRestController {

    private final Logger logger = LoggerFactory.getLogger(FooRestController.class);

    private final RabbitTemplate rabbitTemplate;

    public FooRestController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/foo/{id}")
    public void foo(@PathVariable String id) {
        logger.info("Sending message...");
        Work work = new Work(id);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME, "", work);
    }
}
