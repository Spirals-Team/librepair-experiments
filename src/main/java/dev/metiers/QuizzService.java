package dev.metiers;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.entites.Quizz;
import dev.repositories.quizz.QuizzRepository;

@Service
public class QuizzService {

	private QuizzRepository quizzRepository;

	public QuizzService(QuizzRepository quizzRepository) {
		super();
		this.quizzRepository = quizzRepository;
	}

	public List<Quizz> lister() {
		return quizzRepository.findAll();
	}

	public void save(Quizz quizz) {
		quizzRepository.save(quizz);
	}

	public void update(Quizz quizz) {
		quizzRepository.update(quizz);
	}

	public void delete(Quizz quizz) {
		quizzRepository.delete(quizz);
	}

	public Quizz findQuizzById(Long id) {
		return quizzRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Quizz non trouvé avec l'id " + id));
	}

}