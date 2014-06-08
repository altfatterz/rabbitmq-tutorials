package com.altfatterz.learning.rabbitmq.publishsubscribe;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

// Delivering messages to multiple consumers. Publish/subscribe pattern.

public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // fanout exchange type, all subscribers will get the message. The routing key will be ignored.
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        String message = getMessage(argv);

        //the producer can only send messages to an exchange.
        // The exchange type decides the message routing algorithm.

        // if there are no connected subscribers the message will be discarded

        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());

        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

    private static String getMessage(String[] strings){
        if (strings.length < 1)
            return "info: Hello World!";
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}