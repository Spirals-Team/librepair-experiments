package dev.metiers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.entites.Duel;
import dev.entites.Quizz;
import dev.repositories.duel.DuelRepository;
import dev.repositories.quizz.QuizzRepository;
import dev.repositories.stagiaire.StagiaireRepository;

@Service
public class DuelService {

	private DuelRepository duelRepository;
	private StagiaireRepository stagiaireRepository;
	private QuizzRepository quizzRepository;

	/**
	 * @param duelRepository
	 */
	public DuelService(DuelRepository duelRepository, StagiaireRepository stagiaireRepository,
			QuizzRepository quizzRepository) {
		super();
		this.duelRepository = duelRepository;
		this.stagiaireRepository = stagiaireRepository;
		this.quizzRepository = quizzRepository;
	}

	public void creer(List<Long> idsStagiaires, Quizz quizz) throws Exception {
		Duel duel = new Duel();

		// l'id créé est égal à l'id existant le plus grand auquel
		// on ajoute 1
		Long newId = Collections.max(duelRepository.findAll().stream().map(Duel::getId).collect(Collectors.toList()))
				+ 1;
		duel.setId(newId);
		duel.setStagiaireA(stagiaireRepository.findById(idsStagiaires.get(0)).orElseThrow(Exception::new));
		duel.setStagiaireB(stagiaireRepository.findById(idsStagiaires.get(1)).orElseThrow(Exception::new));
		duel.setQuizz(quizzRepository.findById(quizz.getId()).orElseThrow(Exception::new));
		this.duelRepository.save(duel);
	}

	/**
	 * @return
	 */
	public List<Duel> lister() {
		return duelRepository.findAll();
	}

	public void editer(Duel duelAvecId) {
		this.duelRepository.update(duelAvecId);
	}

	public void supprimer(Duel duel) {
		this.duelRepository.delete(duel);
	}

	public Duel getById(Long id) {
		for (Duel duel : duelRepository.findAll())
			if (duel.getId().equals(id))
				return duel;
		return null;
	}

}
