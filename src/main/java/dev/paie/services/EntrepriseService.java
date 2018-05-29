package dev.paie.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.paie.entite.Entreprise;
import dev.paie.repository.EntrepriseRepository;

@Service
public class EntrepriseService {

	@Autowired
	private EntrepriseRepository entrepriseRepository;

	public List<Entreprise> getLesEntreprises() {
		return entrepriseRepository.findAll();
	}

}
