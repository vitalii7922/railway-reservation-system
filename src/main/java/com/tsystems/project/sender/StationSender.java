package com.tsystems.project.sender;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

@Component
public class StationSender {

    private static final Log log = LogFactory.getLog(StationSender.class);

    public void send() {
        try {
            initSender("second", "updated");
        } catch (Exception e) {
            log.error(e.getCause());
        }
    }

    static void initSender(String queueOrder, String mes) throws NamingException, JMSException {
        Hashtable<String, String> props = new Hashtable<>();
        props.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put("java.naming.provider.url", "tcp://localhost:61616");
        props.put("queue.js-queue", queueOrder);
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
        log.info("---------------" + mes + "-------------------------------");
    }
}
