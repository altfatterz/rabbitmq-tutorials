package com.example.producer;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


// When sending a message we send it to an exchange.
// In this example the producer creates the exchange if it does not exist yet.
// Typically the consumer is creating the queue and the binding to the exchange.

@SpringBootApplication
public class ProducerApplication {

    static final String EXCHANGE_NAME = "messages.topic";

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ProducerApplication.class, args);
        // The AMQP client has some background threads.
        // The close() makes sure that the application quits after the rabbitTemplate.convertAndSend()
        applicationContext.close();
    }

}
