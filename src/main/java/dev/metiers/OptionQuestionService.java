package dev.metiers;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.entites.OptionQuestion;
import dev.repositories.question.OptionQuestionRepository;

/**
 * OptionQuestionService.java
 *
 * @author matth
 */
@Service
public class OptionQuestionService {

	/**
	 * optionQuestionRepository : OptionQuestionRepository
	 */
	private OptionQuestionRepository optionQuestionRepository;

	/**
	 * Constructor of OptionQuestionService.java
	 *
	 * @param optionQuestionRepository
	 */
	public OptionQuestionService(OptionQuestionRepository optionQuestionRepository) {
		super();
		this.optionQuestionRepository = optionQuestionRepository;
	}

	/**
	 * Fonction :
	 *
	 * @return
	 */
	public List<OptionQuestion> lister() {
		return optionQuestionRepository.findAll();
	}

	/**
	 * Fonction :
	 *
	 * @param id
	 * @return
	 */
	public OptionQuestion trouverAvecId(String identifiant) {
		long id = Long.parseLong(identifiant);
		for (OptionQuestion monOption : optionQuestionRepository.findAll()) {
			if (id == monOption.getId()) {
				return monOption;
			}
		}
		return null;
	}

	/**
	 * @return the optionQuestionRepository
	 */
	public OptionQuestionRepository getOptionQuestionRepository() {
		return optionQuestionRepository;
	}

	/**
	 * @param optionQuestionRepository
	 *            the optionQuestionRepository to set
	 */
	public void setOptionQuestionRepository(OptionQuestionRepository optionQuestionRepository) {
		this.optionQuestionRepository = optionQuestionRepository;
	}

}
