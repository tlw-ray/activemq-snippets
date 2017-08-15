package com.tlw.activemq.snippets.amqp.queue;

import org.apache.qpid.jms.JmsConnectionFactory;

import javax.jms.*;

import java.text.DateFormat;
import java.util.Date;

import static com.tlw.activemq.snippets.amqp.queue.QueueConst.*;

/**
 * Created by tlw@winning.com.cn on 2017/8/15.
 */
public class Publish {
    public static void main(String[] args) throws JMSException {
        JmsConnectionFactory factory = new JmsConnectionFactory(CONNECTION_URI);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(QUEUE_NAME);
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        for(int i=0;i<MESSAGE_COUNT;i++){
            TextMessage textMessage = session.createTextMessage("message: " + DateFormat.getTimeInstance().format(new Date()));
            textMessage.setIntProperty("id", i);
            producer.send(textMessage);
        }
        connection.close();
    }
}
