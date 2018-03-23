package asw.inci_manager.inci_manager_gest.services;

import asw.inci_manager.inci_manager_gest.entities.Agent;
import asw.inci_manager.inci_manager_gest.entities.Incidence;
import asw.inci_manager.inci_manager_gest.repositories.IncidenceRepository;
import asw.inci_manager.kafka_manager.producers.KafkaProducer;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class IncidenceService {

	private static final Logger logger = Logger.getLogger(IncidenceService.class);
	
	@Autowired
    KafkaProducer kafkaProducer;

	@Autowired
	IncidenceRepository incidenceRepository;
	
	public void send(Incidence incidence)
	{
		// ToDO: Incorporar un campo topic dinámico o incluirlo como propertie:
		kafkaProducer.send("topic", new Gson().toJson(incidence));
		logger.info("Sending incidence \"" + incidence.getIncidenceName() + "\" to topic '" + "topic" +"'");
	}

	/**
	 * Devuelve las incidencias de un agente pasado por parámetro
	 * @param agent del que quieres obtener las incidencias
	 * @return lista de incidencias
	 */
	public Set<Incidence> getIncidencesFromAgent(Agent agent){
		return incidenceRepository.findIncidenceByAgent(agent);
	}
}
