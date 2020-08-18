package rabbitmq.helloworld;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HelloWorldConsumer {

    private final static String QUEUE_NAME = "hello";
    private final static Logger logger = LoggerFactory.getLogger(HelloWorldConsumer.class);

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        logger.info(" [*] Waiting for messages. To exit press CTRL+C");

        //We're about to tell the server to deliver us the messages from the queue.
        // Since it will push us messages asynchronously, we provide a callback in the form of an object
        // that will buffer the messages until we're ready to use them.
        channel.basicConsume(QUEUE_NAME, true, new DeliverCallback() {
            @Override
            // Called when a basic.deliver is received for this consumer.
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                String message = new String(delivery.getBody(), "UTF-8");
                logger.info(" [x] Received '{}', consumerTag: '{}'", message, consumerTag);
            }
        }, s -> { });
    }
}