package rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Consumer {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        boolean durable = false; // the queue will survive a server restart)
        boolean exclusive = false; // restricted to this connection
        boolean autoDelete = false; // server will delete it when there are no more consumers

        // queue declaration is immutable if it exists, will not be recreated
        // consumer cannot re-declare the same queue with different parameters, it will return exception

        // creates an implicit binding to the default exchange, with the routing key of the queue name.
        channel.queueDeclare(QUEUE_NAME, durable, exclusive, autoDelete, null);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}