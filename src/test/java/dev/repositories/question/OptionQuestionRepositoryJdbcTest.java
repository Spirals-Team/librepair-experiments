package dev.repositories.question;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.DataSourceTestConfig;
import dev.entites.OptionQuestion;

@ContextConfiguration(classes = { OptionQuestionRepositoryJdbc.class, DataSourceTestConfig.class })
@RunWith(SpringRunner.class)
public class OptionQuestionRepositoryJdbcTest {

	@Autowired
	private OptionQuestionRepositoryJdbc optionQuestionRepositoryJdbc;

	// TODO créer les cas de test

	@Test
	public void test_FindAll() {
		assertTrue(!optionQuestionRepositoryJdbc.findAll().isEmpty());
	}

	@Test
	public void test_Save() {
		OptionQuestion option = new OptionQuestion();
		option.setId((long) 12);
		option.setLibelle("LOL");
		option.setOk(false);
		optionQuestionRepositoryJdbc.save(option);
		// assertTrue(optionQuestionRepositoryJdbc.getOptionQuestions().contains(option));
	}

	@Test
	public void test_Update() {
		OptionQuestion option = new OptionQuestion();
		option.setId((long) 12);
		option.setLibelle("LOL");
		option.setOk(false);
		optionQuestionRepositoryJdbc.save(option);

		OptionQuestion newOption = new OptionQuestion();
		newOption.setId((long) 12);
		newOption.setLibelle("Pas LOL");
		newOption.setOk(true);
		optionQuestionRepositoryJdbc.update(newOption);

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
		optionQuestionRepositoryJdbc.save(option);
		optionQuestionRepositoryJdbc.delete(option);
		// assertTrue(!optionQuestionRepositoryJdbc.getOptionQuestions().contains(option));
	}

}
