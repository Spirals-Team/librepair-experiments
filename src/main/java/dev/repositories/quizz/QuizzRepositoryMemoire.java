package dev.repositories.quizz;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import dev.entites.Quizz;

@Service
public class QuizzRepositoryMemoire implements QuizzRepository {

	private List<Quizz> quizzes = new ArrayList<>();

	/**
	 * Initialiser la liste des entites
	 */
	@PostConstruct
	public void initialiser() {
		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdd/jdd-quizz.xml")) {
			this.quizzes = new ArrayList<>(context.getBeansOfType(Quizz.class).values());
		}
		// créer un contexte avec la classe ClassPathXmlApplicationContext
		// Récupérer les données via la méthode context.getBeansOfType(...)
		// fermer le contexte
	}

	// implémenter les méthodes

	@Override
	public List<Quizz> findAll() {
		return this.quizzes;
	}

	@Override
	public void save(Quizz quizz) {
		this.quizzes.add(quizz);
	}

	@Override
	public void update(Quizz quizzAvecId) {
		Long id = quizzAvecId.getId();
		boolean quizzFound = false;
		int i = 0;
		while (i < quizzes.size() && !quizzFound) {
			if (quizzes.get(i).getId().equals(quizzAvecId.getId())) {
				quizzes.set(i, quizzAvecId);
				quizzFound = true;
			}
			i++;
		}
	}

	@Override
	public void delete(Quizz quizz) {
		this.quizzes.remove(quizz);
	}

}