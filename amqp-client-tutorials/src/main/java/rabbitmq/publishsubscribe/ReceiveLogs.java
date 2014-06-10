package rabbitmq.publishsubscribe;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiveLogs {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // declaring on the receiver site the exact same channel.
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // queueDeclare() creates a non-durable, exclusive, auto-delete queue with generated name
        // queueDeclare() also creates an implicit binding to the default exchange ("") with the queue name as the routing key
        String queueName = channel.queueDeclare().getQueue();

        // we create a binding binding between the exchange and the queue.
        // the routing key is ignored for fanout exchange.
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());

            System.out.println(" [x] Received '" + message + "'");
        }
    }
}
