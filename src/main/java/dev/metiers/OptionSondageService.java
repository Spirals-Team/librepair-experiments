package dev.metiers;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.entites.OptionSondage;
import dev.repositories.optionsondage.OptionSondageRepository;

@Service
public class OptionSondageService {

	private OptionSondageRepository optionSondageRepository;

	public OptionSondageService(OptionSondageRepository optionSondageRepository) {
		super();
		this.optionSondageRepository = optionSondageRepository;
	}

	public List<OptionSondage> lister() {
		return optionSondageRepository.findAll();
	}

	public void save(OptionSondage option) {
		optionSondageRepository.save(option);
	}
}
