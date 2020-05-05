package com.tsystems.project.sender;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * author Vitalii Nefedov
 */
@Component
public class ScheduleSender {
    private static final Log log = LogFactory.getLog(ScheduleSender.class);

    /**
     * send message in a queue to "board" application
     *
     * @param stationId station identification
     */
    public void send(String stationId) {
        try {
            StationSender.initSender("first", stationId);
            log.info("station id: " + stationId + " has been send");
        } catch (Exception e) {
            log.error(e.getCause());
        }
    }
}
