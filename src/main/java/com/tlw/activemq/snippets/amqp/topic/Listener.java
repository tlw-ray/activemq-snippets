package com.tlw.activemq.snippets.amqp.topic;

import org.apache.qpid.jms.JmsConnectionFactory;

import javax.jms.*;

import static com.tlw.activemq.snippets.amqp.topic.TopicConst.*;

/**
 * Created by tlw@winning.com.cn on 2017/8/15.
 */
public class Listener{

    public static void main(String[] args) throws JMSException {
        JmsConnectionFactory factory = new JmsConnectionFactory(CONNECTION_URI);
        final Connection connection = factory.createConnection();
        connection.start();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                try {
                    System.out.println("Connection close...");
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(TOPIC_NAME);
        MessageConsumer consumer = session.createConsumer(destination);
        while(true){
            Message message = consumer.receive();
            TextMessage textMessage = (TextMessage)message;
            String content = textMessage.getText();
            System.out.println(content);
        }
    }
}
