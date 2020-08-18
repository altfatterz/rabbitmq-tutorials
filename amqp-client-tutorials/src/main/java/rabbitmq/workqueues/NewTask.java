package rabbitmq.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewTask {

    private static final String TASK_QUEUE_NAME = "task_queue";
    private final static Logger logger = LoggerFactory.getLogger(NewTask.class);

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Message durability - we need to mark both the queue and messages as durable.

        // Note that marking messages as persistent doesn't fully guarantee that a message won't be lost
        // Although it tells RabbitMQ to save the message to disk, there is still a short time window when RabbitMQ
        // has accepted a message and hasn't saved it yet.
        // Also, RabbitMQ doesn't do fsync(2) for every message -- it may be just saved to cache and not really
        // written to the disk.
        // If you need a stronger guarantee then you can use `publisher confirms`

        boolean durable = true; // after server restart queue will be there
        // creates an implicit binding to the default exchange with the routing key equal to the queue
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);

        String message = getMessage(argv);

        // make messages persistent
        channel.basicPublish("", TASK_QUEUE_NAME,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        logger.info(" [x] Sent '{}'", message);

        channel.close();
        connection.close();
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 1) {
            return "Hello World!";
        }
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