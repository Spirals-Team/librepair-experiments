package uo.asw.kafkaConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import uo.asw.dbManagement.model.Incidencia;
import uo.asw.incidashboard.services.IncidenciasService;

@Service
public class KafkaConsumer {
	
	@Autowired
	private IncidenciasService inci;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@KafkaListener(topics = "${kafka.topic}")
	public void processMessage(Incidencia incidencia) {
		inci.addIncidenciaDesdeKafka(incidencia);
		log.info("Incidencia " + incidencia.getId_string() + " recibida desde Apache Kafka");
	}
}
