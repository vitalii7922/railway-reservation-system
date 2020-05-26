package com.tsystems.project.sender;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * author Vitalii Nefedov
 */
@Component
public class StationSender {

    private static final Log log = LogFactory.getLog(StationSender.class);

    /**
     * send message in a queue to "board" application
     */
    public void init() {
        try {
            initSender("second", "updated");
            log.info("station list updated");
        } catch (Exception e) {
            log.error(e.getCause());
        }
    }


    /**
     * initialize sender
     *
     * @param queueName name of a message queue
     * @param mes       message that a queue send
     * @throws NamingException naming exception
     * @throws JMSException    JMS exception
     */
    static void initSender(String queueName, String mes) throws NamingException, JMSException {
        Hashtable<String, String> props = new Hashtable<>();
        props.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put("java.naming.provider.url", "tcp://localhost:61616");
        props.put("queue.js-queue", queueName);
        props.put("connectionFactoryNames", "queueCF");
        Context context = new InitialContext(props);
        QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup("queueCF");
        Queue queue = (Queue) context.lookup("js-queue");
        QueueConnection connection = connectionFactory.createQueueConnection();
        connection.start();
        QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        QueueSender sender = session.createSender(queue);
        TextMessage message = session.createTextMessage(mes);
        sender.send(message);
    }
}
