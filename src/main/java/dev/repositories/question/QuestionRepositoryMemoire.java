package dev.repositories.question;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import dev.entites.Question;

@Repository
public class QuestionRepositoryMemoire implements QuestionRepository {

	private List<Question> questions = new ArrayList<>();

	/**
	 * Initialiser la liste des entites
	 */
	@PostConstruct
	public void initialiser() {
		// TODO créer un contexte avec la classe ClassPathXmlApplicationContext
		// TODO Récupérer les données via la méthode context.getBeansOfType(...)
		// TODO fermer le contexte

		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdd/jdd-question.xml")) {
			Map<String, Question> mapQuestion = context.getBeansOfType(Question.class);
			for (String s : mapQuestion.keySet()) {
				this.questions.add(mapQuestion.get(s));
			}
		}

	}

	@Override
	public List<Question> findAll() {
		// TODO Auto-generated method stub
		return this.questions;
	}

	@Override
	public void save(Question question) {
		// TODO Auto-generated method stub
		this.questions.add(question);

	}

	@Override
	public void update(Question questionAvecId) {
		// TODO Auto-generated method stub
		for (Question question : questions) {
			if (question.getId().equals(questionAvecId.getId())) {
				this.questions.set(questions.indexOf(question), questionAvecId);
			}
		}

	}

	@Override
	public void delete(Question question) {
		// TODO Auto-generated method stub
		Iterator<Question> ite = questions.iterator();
		while (ite.hasNext()) {
			Question quizz = ite.next();
			if (quizz.getId().equals(question.getId())) {
				ite.remove();
			}
		}

	}

	/**
	 * Fonction : Retourne le numéro de version
	 *
	 * @return
	 */
	public String getVersion() {
		return "ver 1.0";
	}

	/**
	 * @return the questions
	 */
	public List<Question> getQuestions() {
		return questions;
	}

	/**
	 * @param questions
	 *            the questions to set
	 */
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	@Override
	public Optional<Question> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}