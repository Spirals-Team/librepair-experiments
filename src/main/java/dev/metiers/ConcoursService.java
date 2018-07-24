package dev.metiers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.entites.Concours;
import dev.repositories.concours.ConcoursDataJpaRepo;

@Service
public class ConcoursService {

	private ConcoursDataJpaRepo concoursRepository;

	public ConcoursService(ConcoursDataJpaRepo concoursRepository) {
		super();
		this.concoursRepository = concoursRepository;
	}

	public List<Concours> list() {
		return concoursRepository.findAll();
	}

	public void ajout(Concours entite) {
		concoursRepository.save(entite);
	}

	public void miseajour(Concours entiteAvecId) {
		concoursRepository.save(entiteAvecId);
	}

	public void suppression(Concours entiteAvecId) {
		concoursRepository.delete(entiteAvecId);
	}

	public Optional<Concours> concoursparid(Long Id) {
		return concoursRepository.findById(Id);
	}
	
	public List<Concours> getConcoursOfStagiaire(Long idStagiaire){
		return concoursRepository.findConcoursByStagiaire(idStagiaire);
	}

}
