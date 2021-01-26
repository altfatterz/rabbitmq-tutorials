package com.example.fooservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooService {

    private final Logger logger = LoggerFactory.getLogger(FooService.class);

    private final RabbitTemplate rabbitTemplate;

    public FooService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/foo")
    public void foo() {
        logger.info("Sending message...");
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME,
                "foo.bar.baz", "Work do be done!");
    }
}
