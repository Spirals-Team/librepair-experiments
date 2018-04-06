package com.app.services;

import com.app.entities.Incident;
import com.app.entities.Agent;
import com.app.repositories.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IncidentService {
    @Autowired
    private IncidentRepository incidentRepository;

    public void saveIncident(Incident incident) {
        incidentRepository.save(incident);
    }

    public List<Incident> getIncidentsByAgent(Agent agent){
        return incidentRepository.findByAgent(agent);
    }
}
