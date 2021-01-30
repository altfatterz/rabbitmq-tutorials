package com.example;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController

public class ProducerRestController {

    private StreamBridge streamBridge;

    public ProducerRestController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @PostMapping("/messages")
    void sendEventWithHeader(@RequestBody String message) throws URISyntaxException {
        URI documentId = new URI("print:123");
        Message<String> event = MessageBuilder.withPayload(message).setHeader("foo", documentId).build();
        streamBridge.send("messagesWithHeaders-out-0", event);
    }
}
