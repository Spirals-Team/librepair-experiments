package dev.metiers;

import java.util.List;

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

	public Duel creer(List<Long> idsStagiaires, Quizz quizz) throws Exception {
		Duel duel = new Duel();

		duel.setStagiaireA(stagiaireRepository.findById(idsStagiaires.get(0)).orElseThrow(Exception::new));
		duel.setStagiaireB(stagiaireRepository.findById(idsStagiaires.get(1)).orElseThrow(Exception::new));
		duel.setQuizz(quizzRepository.findById(quizz.getId()).orElseThrow(Exception::new));
		this.duelRepository.save(duel);

		return duel;
	}

	public Duel creer(Duel duel) {
		this.duelRepository.save(duel);

		return duel;
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

	public Duel findDuelById(Long id) {
		return duelRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Aucun duel trouvé pour l'id " + id));
	}

	public boolean exist(Long id) {
		return duelRepository.findById(id).isPresent();
	}

	public void deleteById(Long id) {
		duelRepository.delete(findDuelById(id));
	}

}
