package dev.metiers;

import org.springframework.stereotype.Service;

import dev.repositories.examen.ExamenRepository;

@Service
public class ExamenService {

	private ExamenRepository examenRepository;

	public ExamenService(ExamenRepository examenRepository) {
		super();
		this.examenRepository = examenRepository;
	}

	public Object lister() {

		return examenRepository.findAll();
	}

}
