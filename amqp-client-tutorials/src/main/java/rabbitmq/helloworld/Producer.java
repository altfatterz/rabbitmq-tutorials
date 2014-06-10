package rabbitmq.helloworld;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Producer {

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

        String message = "Hello World!";

        // here we are publishing using the default exchange (""), which is a direct exchange type
        // here we use the QUEUE_NAME as the routing key
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        // the default exchange makes it seem like it is possible to deliver messages directly to queues,
        // even though that is not technically what is happening.

        channel.close();
        connection.close();

        // A direct exchange delivers messages to queues based on the message routing key.

        // analyse with: rabbitmqctl list_bindings
     }
}