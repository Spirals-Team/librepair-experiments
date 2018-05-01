package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.model.Incidence;
import com.uniovi.model.Incidence.Estado;
import com.uniovi.repositories.IncidencesRepository;

/**
 * Servicio de las incidencias
 * @author Tania Álvarez Díaz
 *
 */
@Service
public class IncidencesService {

	@Autowired
	IncidencesRepository incidencesRepository;

	public List<Incidence> getIncidences() {
		List<Incidence> incidencias = new ArrayList<Incidence>();
		incidencesRepository.findAll().forEach(incidencias::add);
		return incidencias;
	}

	public Incidence getIncidence(Long id) {
		return incidencesRepository.findOne(id);
	}

	public void modifyState(Long id, Estado state) {
		Incidence inci = incidencesRepository.findOne(id);
		inci.setEstado(state);
		incidencesRepository.save(inci);
	}
}
