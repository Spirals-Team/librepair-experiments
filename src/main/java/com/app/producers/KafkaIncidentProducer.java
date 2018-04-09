package com.app.producers;

import javax.annotation.ManagedBean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.app.entities.Incident;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean
public class KafkaIncidentProducer {
	private static final Logger logger = Logger.getLogger(KafkaIncidentProducer.class);
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(Incident incident) {
		String data = convertIncident(incident);
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(incident.getTopic(), data);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.info("Success on sending incident with topic " + incident.getTopic());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("Error on sending message \"" + "\", stacktrace " + ex.getMessage());
			}
		});
	}

	private String convertIncident(Incident incident) {
		String data;
		ObjectMapper mapper = new ObjectMapper();
		try {
			data = mapper.writeValueAsString(incident);
		} catch (JsonProcessingException e) {
			logger.info("Fail on the creation of the incident.");
			data = incident.toString();
		}
		return data;
	}

}
