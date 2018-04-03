package uo.asw.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import uo.asw.dbManagement.model.Incidencia;

@Service
public class KafkaProducer {
	@Autowired
	private KafkaTemplate<String, Incidencia> kafkaTemplate;
	
	@Value("${kafka.topic}")
	String kafkaTopic = "asw-test";
	
	public void send(Incidencia incidencia) {
	    System.out.println("Enviando a kafka = " + incidencia.toStringJson()); //quitar o eviar a logger
	    kafkaTemplate.send(kafkaTopic, incidencia);
	}
}
