package dev.metiers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.entites.Concours;
import dev.repositories.concours.ConcoursRepository;

@Service
public class ConcoursService {

	private ConcoursRepository concoursRepository;

	public ConcoursService(ConcoursRepository concoursRepository) {
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
		concoursRepository.update(entiteAvecId);
	}

	public void suppression(Concours entiteAvecId) {
		concoursRepository.delete(entiteAvecId);
	}

	public Optional<Concours> concoursparid(Long Id) {
		return concoursRepository.findById(Id);
	}

}
