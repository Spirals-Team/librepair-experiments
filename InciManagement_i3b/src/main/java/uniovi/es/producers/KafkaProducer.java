package uniovi.es.producers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import uniovi.es.entities.Incidence;

import javax.annotation.ManagedBean;

@ManagedBean
public class KafkaProducer {

	private static final Logger logger = Logger.getLogger(KafkaProducer.class);

	@Autowired
	private KafkaTemplate<String, Incidence> kafkaTemplate;

	public void send(String topic, Incidence incidence) {
		ListenableFuture<SendResult<String, Incidence>> future = kafkaTemplate.send(topic, incidence);
		future.addCallback(new ListenableFutureCallback<SendResult<String, Incidence>>() {
			@Override
			public void onSuccess(SendResult<String, Incidence> result) {
				logger.info("Success on sending message \"" + incidence + "\" to topic " + topic);
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("Error on sending message \"" + incidence + "\", stacktrace " + ex.getMessage());
			}
		});
	}

}
