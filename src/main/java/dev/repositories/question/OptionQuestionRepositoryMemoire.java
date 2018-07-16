package dev.repositories.question;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import dev.entites.OptionQuestion;

@Repository
public class OptionQuestionRepositoryMemoire implements OptionQuestionRepository {

	private List<OptionQuestion> optionQuestions = new ArrayList<>();

	/**
	 * Initialiser la liste des entites
	 */
	@PostConstruct
	public void initialiser() {
		// TODO créer un contexte avec la classe ClassPathXmlApplicationContext
		// TODO Récupérer les données via la méthode context.getBeansOfType(...)
		// TODO fermer le contexte

		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdd/jdd-question.xml")) {
			Map<String, OptionQuestion> mapOption = context.getBeansOfType(OptionQuestion.class);
			for (String s : mapOption.keySet()) {
				this.optionQuestions.add(mapOption.get(s));
			}
		}

	}

	@Override
	public List<OptionQuestion> findAll() {
		// TODO Auto-generated method stub
		return this.optionQuestions;
	}

	@Override
	public void save(OptionQuestion optionQuestion) {
		// TODO Auto-generated method stub
		this.optionQuestions.add(optionQuestion);
	}

	@Override
	public void update(OptionQuestion optionQuestionAvecId) {
		// TODO Auto-generated method stub
		for (OptionQuestion option : optionQuestions) {
			if (option.getId().equals(optionQuestionAvecId.getId())) {
				optionQuestions.set(optionQuestions.indexOf(option), optionQuestionAvecId);
			}
		}

	}

	@Override
	public void delete(OptionQuestion optionQuestion) {
		// TODO Auto-generated method stub
		Iterator<OptionQuestion> ite = optionQuestions.iterator();
		while (ite.hasNext()) {
			OptionQuestion option = ite.next();
			if (option.getId().equals(optionQuestion.getId())) {
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
	 * @return the optionQuestions
	 */
	public List<OptionQuestion> getOptionQuestions() {
		return optionQuestions;
	}

	/**
	 * @param optionQuestions
	 *            the optionQuestions to set
	 */
	public void setOptionQuestions(List<OptionQuestion> optionQuestions) {
		this.optionQuestions = optionQuestions;
	}

	@Override
	public Optional<OptionQuestion> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
