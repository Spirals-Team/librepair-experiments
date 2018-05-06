package uniovi.es.services;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniovi.es.entities.Agent;
import uniovi.es.repository.AgentsRepository;

@Service
public class InsertSampleDataService {

	@Autowired
	private AgentsRepository agentsRepository;

	@PostConstruct
	public void init() {
		Agent agent1 = new Agent("11111111A","123456","Pepe","pepe@gmail.com","Oviedo","Person","1");
		agentsRepository.save(agent1);
		Agent sensor = new Agent("sensor","123","Temperature Sensor","pepe@gmail.com","Oviedo","Sensor","3");
		agentsRepository.save(sensor);
//		Agent agent2 = new Agent("22222222B","123456","Jose","jose@gmail.com","Gijón","Person","1");
//		agentsRepository.save(agent2);
//		Agent agent3 = new Agent("33333333C","123456","Pelayo","pelayo@gmail.com","Pravia","Person","1");
//		agentsRepository.save(agent3);		
	}
}
