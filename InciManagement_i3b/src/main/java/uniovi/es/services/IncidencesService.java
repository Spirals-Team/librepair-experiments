package uniovi.es.services;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniovi.es.entities.Incidence;
import uniovi.es.entities.Operator;
import uniovi.es.producers.KafkaProducer;
import uniovi.es.repositories.IncidencesRepository;
import uniovi.es.repositories.OperatorRepository;

@Service
public class IncidencesService {

	private final static String topic = "incidences";
	
	@Autowired
	private IncidencesRepository incidentsRepository;
	@Autowired
	private OperatorRepository operatorRepository;
	@Autowired
	private KafkaProducer kafkaProducer;

	public void addIncident(Incidence incident) {
		incidentsRepository.save(incident);
		
		kafkaProducer.send(topic, incident);
	}

	public List<Incidence> getAgentIncidents(String agentName) {
		return incidentsRepository.getIncidentsByUsername(agentName);
	}
	
	public String getRandomOperator() {
		List<Operator> operator = operatorRepository.findAll();
		Random rand = new Random();
		return operator.get(rand.nextInt(operator.size())).getOperatorId();
	}

}
