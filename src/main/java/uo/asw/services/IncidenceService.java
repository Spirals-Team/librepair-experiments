package uo.asw.services;


import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uo.asw.entities.Incidence;
import uo.asw.kafka.KafkaProducer;
import uo.asw.repositories.IncidenceRepository;

@Service
public class IncidenceService {

	@Autowired
	private IncidenceRepository incidenceRepository;
	
	@Autowired
	private KafkaProducer kafkaProducer;
	
	
	public void addIncidence(Incidence incidence) {
		incidenceRepository.save(incidence);
		kafkaProducer.send("gygw6fys-Incidencias",incidence.toJSON());
	}
	
	public Incidence getIncidenceById(ObjectId id)
	{
		return incidenceRepository.findOne(id);
	}
	
	public List<Incidence> getIncidencesByAgentEmail(String email)
	{
		return incidenceRepository.findByEmailAgente(email);
	}
	
	public Incidence saveIncidence (Incidence incidence)
	{
		return incidenceRepository.save(incidence);
	}
}
