package com.tlw.activemq.snippets.amqp.topic;

import org.apache.qpid.jms.JmsConnectionFactory;

import javax.jms.*;

import static com.tlw.activemq.snippets.amqp.topic.TopicConst.*;

/**
 * Created by tlw@winning.com.cn on 2017/8/15.
 */
public class Publish{
    public static void main(String[] args) throws JMSException {
        JmsConnectionFactory factory = new JmsConnectionFactory(CONNECTION_URI);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(TOPIC_NAME);
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        for(int i=0;i<MESSAGE_COUNT;i++){
            TextMessage textMessage = session.createTextMessage("message: " + i);
            textMessage.setIntProperty("id", i);
            producer.send(textMessage);
        }
        connection.close();
    }
}
