package asw.inci_manager.inci_manager_gest.services;

import asw.inci_manager.inci_manager_gest.entities.Agent;
import asw.inci_manager.inci_manager_gest.entities.Incidence;
import asw.inci_manager.inci_manager_gest.repositories.AgentRepository;
import asw.inci_manager.inci_manager_gest.repositories.IncidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;

@Service
public class InsertDataSample {

    @Autowired
    AgentRepository agentRepository;

    @Autowired
    IncidenceRepository incidenceRepository;

    @PostConstruct
    public void init(){
        Agent paco =new Agent("Paco González","123456","","paco@gmail.com","paco","Person");
        Agent pepe = new Agent("Pepe Fernandez","123456","","pepe@gmail.com","pepe","Person");
        Agent sensor_123 = new Agent("Sensor_123 2018","123456","43.361368, -5.853591","admin@sensores.com","sensor_123","Sensor");
        Agent medioambiente = new Agent("Ministerio medioambiente","123456","43.359486, -5.846986","ambiente@ministerio.com","medioambiente","Entity");
        Agent spacex = new Agent("Space X sensor model A","123456","33.921209, -118.327940","musk@spacex.com","spacex","Sensor");

        agentRepository.save(paco);
        agentRepository.save(pepe);
        agentRepository.save(sensor_123);
        agentRepository.save(medioambiente);
        agentRepository.save(spacex);

        Incidence inci1 = new Incidence(paco, "Gotera en el techo", "Debido a una rotura de cañería","43.6735, 58.2452",new HashSet<String>(){{add("gotera");add("agua");}});
        Incidence inci2 = new Incidence(paco, "Rotura de cañería", "Se ha roto totalmente una cañería de plomo","43.6735, 58.2452",new HashSet<String>(){{add("rotura");add("agua");add("cañería");}});
        Incidence inci3 = new Incidence(pepe, "Rotura de pared", "La que saliao pollito","43.789, 59.146",new HashSet<String>(){{add("rotura");add("pared");}});
        Incidence inci4 = new Incidence(pepe, "Fuego en papelera", "Una colilla ha prendido fuego una papelera","44.731, 39.125",new HashSet<String>(){{add("fuego");add("calle");}});

        incidenceRepository.save(inci1);
        incidenceRepository.save(inci2);
        incidenceRepository.save(inci3);
        incidenceRepository.save(inci4);

    }
}
