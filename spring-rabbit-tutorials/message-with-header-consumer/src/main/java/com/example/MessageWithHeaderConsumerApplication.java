package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.net.URI;
import java.util.function.Consumer;

@SpringBootApplication
public class MessageWithHeaderConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageWithHeaderConsumerApplication.class, args);
    }

    @Bean
    public Consumer<Message<String>> messagesWithHeaders() {
        return message -> {

            MessageHeaders headers = message.getHeaders();
            String fooValue = headers.get("foo", String.class);

            String payload = message.getPayload();

            System.out.println("foo:" + fooValue);
            System.out.println("payload:" + payload);
        };
    }
}
