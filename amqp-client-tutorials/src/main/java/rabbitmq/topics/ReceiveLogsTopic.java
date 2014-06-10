package rabbitmq.topics;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

// topic exchange is powerful and can behave like other exchanges;

// When a queue is bound with "#" (hash) binding key - it will receive all the messages, regardless of the routing key - like in fanout exchange.

// When special characters "*" (star) and "#" (hash) aren't used in bindings, the topic exchange will behave just like a direct one.

public class ReceiveLogsTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) {
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            String queueName = channel.queueDeclare().getQueue();

            if (argv.length < 1){
                System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
                System.exit(1);
            }

            // the logic behind the topic exchange is similar to the direct exchange:
            // a message sent with a particular routing key will be delivered to all the queues, that are bound
            // with a matching binding key.

            // two special cases for binding keys:
            // * - can substitute for exactly one word
            // # - can substitute for zero or more words

            for(String bindingKey : argv){
                channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
            }

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, true, consumer);

            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                String routingKey = delivery.getEnvelope().getRoutingKey();

                System.out.println(" [x] Received '" + routingKey + "':'" + message + "'");
            }
        }
        catch  (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (Exception ignore) {}
            }
        }
    }
}