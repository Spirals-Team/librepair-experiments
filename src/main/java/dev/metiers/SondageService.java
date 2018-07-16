package dev.metiers;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.entites.Sondage;
import dev.repositories.sondage.SondageRepository;

@Service
public class SondageService {

	private SondageRepository sondageRepository;

	public SondageService(SondageRepository sondageRepository) {
		super();
		this.sondageRepository = sondageRepository;
	}

	public List<Sondage> lister() {
		return sondageRepository.findAll();
	}

	public void save(Sondage s) {
		sondageRepository.save(s);
	}

	public Sondage findById(Long id) throws Exception {
		return sondageRepository.findById(id).orElseThrow(Exception::new);
	}

}
