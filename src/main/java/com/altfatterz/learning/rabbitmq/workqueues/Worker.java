package com.altfatterz.learning.rabbitmq.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Worker {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // This tells RabbitMQ not to give more than one message to a worker at a time
        // In other words, don't dispatch a new message to a worker until it has processed and acknowledged the previous one.
        channel.basicQos(1);

        QueueingConsumer consumer = new QueueingConsumer(channel);

        // using the explicit acknowledgment model instead of the automatic acknowledgement model
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());

            System.out.println(" [x] Received '" + message + "'");
            doWork(message);
            System.out.println(" [x] Done");

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(1000);
        }
    }
}