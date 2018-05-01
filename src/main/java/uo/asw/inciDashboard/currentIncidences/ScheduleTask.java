package uo.asw.inciDashboard.currentIncidences;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import uo.asw.dbManagement.model.Agent;
import uo.asw.dbManagement.model.Incidence;
import uo.asw.dbManagement.model.Operator;

@Service
public class ScheduleTask { // TODO - quitar

    @Autowired
    private SimpMessagingTemplate template;

    // this will send a message to an endpoint on which a client can subscribe
//	@Scheduled(fixedRate = 10000)
//	public void trigger() {
//		añadirIncidencias();
//	}
	
	int cont = 1;
    
    /** Metodo creado para añadir incidencias a la lista con el fin de hacer pruebas
	 * 
	 * @param incidence
	 */
	public void añadirIncidencias() {
		Agent agent1 = new Agent("31668313G", "1234", "Person");
		agent1.setName("Juan");
		agent1.setEmail("email@email.com");
		Agent agent2 = new Agent("1245478", "1234", "Person");
		agent1.setName("Pepe");
		agent1.setEmail("email@email.com");
		Agent agent3 = new Agent("71452145", "1234", "Person");
		agent1.setName("Pedro");
		agent1.setEmail("email@email.com");
		Agent agent4 = new Agent("7452145241", "1234", "Person");
		agent1.setName("Jaime");
		agent1.setEmail("email@email.com");
		Operator opreator2 = new Operator("AAAAAAA2", "Juan");
		opreator2.setPassword("123456");
		Operator opreator3 = new Operator("AAAAAAA3", "Francisco");
		opreator3.setPassword("123456");
		Operator opreator4 = new Operator("AAAAAAA4", "Rodrigo");
		opreator4.setPassword("123456");
		Operator opreator5 = new Operator("AAAAAAA5", "Pepe");
		opreator5.setPassword("123456");
		
		Incidence i1 = new Incidence(cont++, "XXX", agent1, opreator5, "Fuego", "Coche ardiendo", "43.35,-5.85", null, null, "Abierta", "Mucho humo", "12/02/2018", true);
		Incidence i2 = new Incidence(cont++, "ZZZ", agent2, opreator2, "Inundacion", "Calle inundada lluvia", "43.56,-5.90", null, null, "Abierta", "Mucha agua", "12/02/2018", true);
		Incidence i3 = new Incidence(cont++, "YYY", agent3, opreator3, "Accidente", "Colision entre dos coches", "43.29,-5.69", null, null, "Abierta", "Ya esta solucionado", "12/02/2018", true);
		Incidence i4 = new Incidence(cont++, "WWW", agent4, opreator4, "Accidente", "Colision entre coche y camión", "43.10,-5.69", null, null, "Abierta", "Ya esta solucionado", "12/02/2018", false);
		
		
		this.template.convertAndSend("/topic/incidences", i1);
		this.template.convertAndSend("/topic/incidences", i2);
		this.template.convertAndSend("/topic/incidences", i3);
		this.template.convertAndSend("/topic/incidences", i4);
	}

}
