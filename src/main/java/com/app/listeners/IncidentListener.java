package com.app.listeners;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.annotation.ManagedBean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.app.entities.Incident;
import com.app.services.IncidentService;

@ManagedBean
public class IncidentListener {
    @Autowired
    private IncidentService incidentService;

    private static final Logger logger = Logger.getLogger(IncidentListener.class);

    @KafkaListener(topics = "ACCIDENT")
    public void listenAccident(String data) {
        logger.info("New message received: \"" + data + "\"");
        saveIncidentFromKafka(data);
    }

    @KafkaListener(topics = "FIRE")
    public void listenFire(String data) {
        logger.info("New message received: \"" + data + "\"");
        saveIncidentFromKafka(data);
    }

    @KafkaListener(topics = "ALTERCATION")
    public void listenAltercation(String data) {
        logger.info("New message received: \"" + data + "\"");
        saveIncidentFromKafka(data);
    }

    @KafkaListener(topics = "MEDICAL_EMERGENCY")
    public void listenMedical(String data) {
        logger.info("New message received: \"" + data + "\"");
        saveIncidentFromKafka(data);
    }

    @KafkaListener(topics = "METEREOLOGICAL_PHENOMENON")
    public void listenMeteo(String data) {
        logger.info("New message received: \"" + data + "\"");
        saveIncidentFromKafka(data);
    }

    @KafkaListener(topics = "OTHER")
    public void listenOther(String data) {
        logger.info("New message received: \"" + data + "\"");
        saveIncidentFromKafka(data);
    }

    private void saveIncidentFromKafka(String data) {
        try {
            ObjectMapper obj = new ObjectMapper();
            Incident incident = obj.readValue(data.getBytes(), Incident.class);
            incidentService.saveIncident(incident);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}