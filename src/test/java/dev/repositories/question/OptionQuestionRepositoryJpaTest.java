package dev.repositories.question;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.JpaTestConfig;
import dev.entites.OptionQuestion;

@ContextConfiguration(classes = { OptionQuestionRepositoryJpa.class, JpaTestConfig.class })
@RunWith(SpringRunner.class)
public class OptionQuestionRepositoryJpaTest {

	@Autowired
	private OptionQuestionRepository optionQuestionRepository;

	// TODO créer les cas de test

	@Test
	@Ignore
	public void test_FindAll() {
		assertTrue(!optionQuestionRepository.findAll().isEmpty());
	}

	@Test
	public void test_Save() {
		OptionQuestion option = new OptionQuestion();
		option.setId((long) 12);
		option.setLibelle("LOL");
		option.setOk(false);
		optionQuestionRepository.save(option);
		// assertTrue(optionQuestionRepositoryJdbc.getOptionQuestions().contains(option));
	}

	@Test
	public void test_Update() {
		OptionQuestion option = new OptionQuestion();
		option.setId((long) 12);
		option.setLibelle("LOL");
		option.setOk(false);
		optionQuestionRepository.save(option);

		OptionQuestion newOption = new OptionQuestion();
		newOption.setId((long) 12);
		newOption.setLibelle("Pas LOL");
		newOption.setOk(true);
		optionQuestionRepository.update(newOption);

		// int id =
		// optionQuestionRepositoryJdbc.getOptionQuestions().indexOf(newOption);

		// assertTrue(optionQuestionRepositoryJdbc.getOptionQuestions().get(id).getLibelle().equals("Pas
		// LOL")
		// &&
		// optionQuestionRepositoryJdbc.getOptionQuestions().get(id).getOk());

	}

	@Test
	public void test_Delete() {
		OptionQuestion option = new OptionQuestion();
		option.setId((long) 12);
		option.setLibelle("LOL");
		option.setOk(false);
		optionQuestionRepository.save(option);
		optionQuestionRepository.delete(option);
		// assertTrue(!optionQuestionRepositoryJdbc.getOptionQuestions().contains(option));
	}

}
