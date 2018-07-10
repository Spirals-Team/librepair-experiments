package dev.metiers;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.entites.Stagiaire;
import dev.repositories.stagiaire.StagiaireRepository;

@Service
public class StagiaireService {

	private StagiaireRepository stagiaireRepository;

	public StagiaireService(StagiaireRepository stagiaireRepository) {
		super();
		this.stagiaireRepository = stagiaireRepository;
	}

	public List<Stagiaire> lister() {
		return stagiaireRepository.findAll();
	}

}
