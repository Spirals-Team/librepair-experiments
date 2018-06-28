package inciManager.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import inciManager.entities.Incidencia;

import javax.annotation.ManagedBean;

/**
 * Created by herminio on 26/12/16.
 */
@ManagedBean
public class SendIncidenceClass implements SendIncidence {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private void send(String topic, String data) {
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				System.out.println("Success on sending message \"" + data + "\" to topic " + topic);
			}

			@Override
			public void onFailure(Throwable ex) {
				System.err.println("Error on sending message \"" + data + "\", stacktrace " + ex.getMessage());
			}
		});
	}

	@Override
	public void sendIncidence(Incidencia incidencia) {
		send("incidencia", JSONParser.parseIncidence(incidencia));
	}

}