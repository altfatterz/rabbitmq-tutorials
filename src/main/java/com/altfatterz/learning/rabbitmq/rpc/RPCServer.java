package com.altfatterz.learning.rabbitmq.rpc;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.BasicProperties;


// If the RPC server is too slow, you can scale up by just running another one

public class RPCServer {

    private static final String RPC_QUEUE_NAME = "rpc_queue";

    private static int fib(int n) {
        if (n ==0) return 0;
        if (n == 1) return 1;
        return fib(n-1) + fib(n-2);
    }

    public static void main(String[] argv) {
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");

            connection = factory.newConnection();
            channel = connection.createChannel();

            // this queue declaration also creates an implicit binding to the default exchange with the queue name as binding key
            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

            // This tells RabbitMQ not to give more than one message to the server at a time
            // In other words, don't dispatch a new message to the server until it has processed and acknowledged the previous one.
            channel.basicQos(1);

            QueueingConsumer consumer = new QueueingConsumer(channel);

            // using the explicit acknowledgement model
            channel.basicConsume(RPC_QUEUE_NAME, false, consumer);

            System.out.println(" [x] Awaiting RPC requests");

            while (true) {
                String response = null;

                QueueingConsumer.Delivery delivery = consumer.nextDelivery();

                BasicProperties props = delivery.getProperties();
                BasicProperties replyProps = new BasicProperties
                        .Builder()
                        .correlationId(props.getCorrelationId())
                        .build();

                try {
                    String message = new String(delivery.getBody(),"UTF-8");
                    int n = Integer.parseInt(message);

                    System.out.println(" [.] fib(" + message + ")");
                    response = "" + fib(n);
                }
                catch (Exception e){
                    System.out.println(" [.] " + e.toString());
                    response = "";
                }
                finally {
                    channel.basicPublish( "", props.getReplyTo(), replyProps, response.getBytes("UTF-8"));

                    // here it is possible that the server dies without sending an acknowledgment for the request.
                    // if that happens the restarted RPC server will process the request again.
                    // That's why on the client we must handle the duplicate responses gracefully, and the RPC should ideally be idempotent.

                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
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
