package inciManager.uo.asw.kafka.producers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import inciManager.uo.asw.dbManagement.model.Incidencia;

@Service
public class KafkaProducer {
	@Autowired
	private KafkaTemplate<String, Incidencia> kafkaTemplate;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${kafka.topic}")
	String kafkaTopic;
	
	/**
	 * Envia por kafka una incidencia, ademas se guarda informacion en Manager.log
	 * se puede encontrar dentro del propio proyecto
	 * @param incidencia que se desea enviar
	 */
	public void send(Incidencia incidencia) {
		log.info("Enviando a kafka = " + incidencia.toStringJson()); 
	    kafkaTemplate.send(kafkaTopic, incidencia);
	}
}
