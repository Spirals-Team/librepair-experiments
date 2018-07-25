package dev.metiers;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import dev.controller.api.viewModels.passageConcours.PassageConcoursVm;
import dev.entites.Concours;
import dev.entites.PassageConcours;
import dev.entites.Stagiaire;
import dev.repositories.concours.PassageConcoursRepository;
import dev.repositories.concours.ResultatQuestionConcoursRepository;

@Service
public class PassageConcoursService {

	private PassageConcoursRepository passageConcoursRepository;
	private ResultatQuestionConcoursRepository resultatQuestionConcoursRepository;

	public PassageConcoursService(PassageConcoursRepository passageConcoursRepository, ResultatQuestionConcoursRepository resultatQuestionConcoursRepository) {
		super();
		this.passageConcoursRepository = passageConcoursRepository;
		this.resultatQuestionConcoursRepository = resultatQuestionConcoursRepository;
	}

	public Long createPassage(PassageConcoursVm passageConcoursVm) {
		
		PassageConcours tempPassage = new PassageConcours();
		tempPassage.setConcours(new Concours(passageConcoursVm.getConcours()));
		tempPassage.setStagiaire(new Stagiaire(passageConcoursVm.getStagiaire()));
		tempPassage.setDatePassage(LocalDateTime.now());
		
		passageConcoursRepository.save(tempPassage);
		
		return tempPassage.getId();
		
	}
	
}
