package ru.aserdyuchenko.testTask;

import org.apache.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class Start {
    static ConnectionToWebsite connectionToWebsite = new ConnectionToWebsite();
    static JDBC jdbc = new JDBC();
    final static Logger logger = Logger.getLogger(Start.class);

    public static void main(String[] args) {
        logger.info("Entering the main method.");
        ScheduledExecutorService execService = Executors.newScheduledThreadPool(5);
        execService.scheduleAtFixedRate(() -> {
            try {
                connectionToWebsite.getDataFromWebsite();
                logger.info("Connection to sql.ru successful.");
                jdbc.add(connectionToWebsite.getOffers());
                logger.info("Add data in DB successful.");
            } catch (Exception e) {
                logger.error("Error. Message: " + e.getMessage());
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.DAYS);
    }

    public void staticStart() {
        logger.info("Entering the main method.");
        ScheduledExecutorService execService = Executors.newScheduledThreadPool(5);
        execService.scheduleAtFixedRate(() -> {
            try {
                connectionToWebsite.getDataFromWebsite();
                logger.info("Connection to sql.ru successful.");
                jdbc.add(connectionToWebsite.getOffers());
                logger.info("Add data in DB successful.");
            } catch (Exception e) {
                logger.error("Error. Message: " + e.getMessage());
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.DAYS);
    }
}
