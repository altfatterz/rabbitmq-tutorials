package rabbitmq.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Worker {

    private static final String TASK_QUEUE_NAME = "task_queue";
    private static final Logger logger = LoggerFactory.getLogger(NewTask.class);

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        logger.info(" [*] Waiting for messages. To exit press CTRL+C");


        // Fair dispatch
        // if for example all odd messages are heavy and even messages are light, one worker will be constantly busy
        // and the other one will do hardly any work -- RabbitMQ just blindly dispatches every n-th message to the n-th consumer.

        // This tells RabbitMQ not to give more than one message to a worker at a time.
        // In other words, don't dispatch a new message to a worker until it has processed and acknowledged the previous one.
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            logger.info(" [x] Received '{}'", message);
            try {
                doWork(message);
            } finally {
                logger.info(" [x] Done");
                // An acknowledgement is sent back by the consumer to tell RabbitMQ that a particular
                // message has been received, processed and that RabbitMQ is free to delete it.

                // If a consumer dies (its channel is closed, connection is closed, or TCP connection is lost) without
                // sending an ack, RabbitMQ will understand that a message wasn't processed fully and will re-queue it.
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };

        // Manual message acknowledgments are turned on by default.
        // In previous examples we explicitly turned them off via the autoAck=true flag

        // Here we set it this flag to false and send a proper acknowledgment from the worker, once we're done with a task.
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });
    }

    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}