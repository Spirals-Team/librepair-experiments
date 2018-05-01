package uo.asw.inciDashboard.currentIncidences;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import uo.asw.dbManagement.model.Agent;
import uo.asw.dbManagement.model.Incidence;
import uo.asw.dbManagement.model.Operator;

@Service
public class ReceiveFilteredIncidenceImpl implements ReceiveFilteredIncidence {
	
	@Autowired
    private SimpMessagingTemplate template;

	int cont = 1;
	
	@Override
	public void receiveFilteredIncidence(Incidence incidence) {
		System.err.println(incidence);
		
//		Agent agent1 = new Agent("31668313G", "1234", "Person");
//		agent1.setName("Juan");
//		agent1.setEmail("email@email.com");
//		
//		Operator opreator5 = new Operator("AAAAAAA5", "Pepe");
//		opreator5.setPassword("123456");
//		
//		Incidence i1 = new Incidence(cont++, "XXX", agent1, opreator5, "Fuego", "Coche ardiendo", "43.35,-5.85", null, null, "Abierta", "Mucho humo", "12/02/2018", true);
		
		
		//this.template.convertAndSend("/topic/incidences", incidence);
		this.template.convertAndSend("/topic/incidences", incidence);
	}
	
}
