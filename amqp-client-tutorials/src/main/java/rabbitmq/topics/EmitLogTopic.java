package rabbitmq.topics;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

// In our logging system we might want to subscribe to not only logs based on severity,
// but also based on the source which emitted the log.


// Answers:
// the "*" binding will not catch a message sent with an empty routing key.
// the "#.*" binding will call catch a message sent with a routing key ".." and also "."
// the difference between "a.*.#" from "a.#" binding keys are that the first will not catch a message with routing key "a" while the second will
public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) {
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");

            connection = factory.newConnection();
            channel = connection.createChannel();

            // messages sent to a topic exchange can't have an arbitrary routing_key - it must be a list of words, delimited by dots.
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            // example routing keys: "stock.usd.nyse", "nyse.vmw", "quick.orange.rabbit"

            String routingKey = "a";
            String message = "yooman";

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");

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

    private static String getRouting(String[] strings){
        if (strings.length < 1)
            return "anonymous.info";
        return strings[0];
    }

    private static String getMessage(String[] strings){
        if (strings.length < 2)
            return "Hello World!";
        return joinStrings(strings, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0 ) return "";
        if (length < startIndex ) return "";
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}