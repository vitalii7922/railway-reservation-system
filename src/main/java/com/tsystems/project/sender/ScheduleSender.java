package com.tsystems.project.sender;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class ScheduleSender {
    private static final Log log = LogFactory.getLog(ScheduleSender.class);

    public void send(String stationId) {
        try {
            StationSender.initSender("first", stationId);
        } catch (Exception e) {
           log.error(e.getCause());
        }
    }
}
