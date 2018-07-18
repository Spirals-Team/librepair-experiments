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

	public void save(Stagiaire stagiaire) {
		stagiaireRepository.save(stagiaire);
	}

	public void update(Stagiaire StagiaireAvecId) {
		stagiaireRepository.update(StagiaireAvecId);
	}

	public void delete(Stagiaire stagiaire) {
		stagiaireRepository.delete(stagiaire);
	}

	public Stagiaire findStagiaireById(Long id) {
		return stagiaireRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("stagiaire non trouvé avec l'id " + id));

	}

}
