package dev.metiers;

import java.util.List;

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

	public List<Concours> lister() {
		return concoursRepository.findAll();
	}

}
