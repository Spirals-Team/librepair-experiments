package uo.asw.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import uo.asw.apacheKafka.producer.KafkaProducer;

@Service
public class InsertIncidencesUsingKafkaService {

	@Autowired
	private KafkaProducer kafkaProducer;

	// this will send a message to an endpoint on which a client can subscribe
	@Scheduled(fixedRate = 10000)
	public void trigger() {
		int i = 1;

		String identifier = UuidGenerator.getUuid();
		String jsonIncidenceWithOutTagFuego = "{"
				+ "\"identifier\": \""+ identifier + "\","
				+ "\"login\": \"31668313G\","
				+ "\"password\": \"1234\","
				+ "\"kind\": \"Person\","
				+ "\"name\": \"IncidenciaPrueba"+i+"\","
				+ "\"description\": \"Descripcion\","
				+ "\"tags\": [\"calor\"]"
			+ "}";
//		String jsonIncidenceWithOutTagFuego = 
//				"\"identifier\": \""+ identifier + "\","
//				+ "\"login\": \"31668313G\","
//				+ "\"password\": \"1234\","
//				+ "\"kind\": \"Person\","
//				+ "\"name\": \"IncidenciaPrueba"+i+"\","
//				+ "\"description\": \"Descripcion\","
//				+ "\"tags\": [\"calor\"]";

		kafkaProducer.send("incidences", jsonIncidenceWithOutTagFuego);
	}
	
}