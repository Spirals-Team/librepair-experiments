package dev.repositories.question;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.DataSourceTestConfig;
import dev.entites.OptionQuestion;
import dev.entites.Question;

@ContextConfiguration(classes = { QuestionRepositoryJdbc.class, DataSourceTestConfig.class })
@RunWith(SpringRunner.class)
public class QuestionRepositoryJdbcTest {

	@Autowired
	private QuestionRepositoryJdbc questionRepositoryJdbc;

	// TODO créer les cas de test

	@Test
	public void test_FindAll() {
		// assertTrue(!questionRepositoryJdbc.findAll().isEmpty());
	}

	@Test
	public void test_Save() {
		Question quizz = new Question();
		quizz.setId((long) 123456789);

		OptionQuestion option = new OptionQuestion();
		option.setId((long) 12);
		option.setLibelle("LOL");
		option.setOk(false);

		List<OptionQuestion> list = new ArrayList<OptionQuestion>();
		list.add(option);

		quizz.setOptions(list);

		questionRepositoryJdbc.save(quizz);

		// assertTrue(questionRepositoryJdbc.getQuestions().contains(quizz));
	}

	@Test
	public void test_Update() {
		Question quizz = new Question();
		quizz.setId((long) 123456789);
		quizz.setTitre("Trop bien !");

		OptionQuestion option = new OptionQuestion();
		option.setId((long) 12);
		option.setLibelle("LOL");
		option.setOk(false);

		List<OptionQuestion> list = new ArrayList<OptionQuestion>();
		list.add(option);

		quizz.setOptions(list);

		questionRepositoryJdbc.save(quizz);

		Question quizz2 = new Question();
		quizz2.setId((long) 123456789);
		quizz2.setTitre("Trop bien ?");

		OptionQuestion option2 = new OptionQuestion();
		option2.setId((long) 12);
		option2.setLibelle("Pas LOL");
		option2.setOk(true);

		List<OptionQuestion> list2 = new ArrayList<OptionQuestion>();
		list2.add(option2);

		quizz2.setOptions(list2);

		questionRepositoryJdbc.update(quizz2);

		// int id = questionRepositoryJdbc.getQuestions().indexOf(quizz2);

		// assertTrue(questionRepositoryJdbc.getQuestions().get(id).getTitre().equals("Trop
		// bien ?"));
		// assertTrue(questionRepositoryJdbc.getQuestions().get(id).getOptions().get(0).getId().equals((long)
		// 12));
		// assertTrue(questionRepositoryJdbc.getQuestions().get(id).getOptions().get(0).getOk());
		// assertTrue(questionRepositoryJdbc.getQuestions().get(id).getOptions().get(0).getLibelle().equals("Pas
		// LOL"));

	}

	@Test
	public void test_Delete() {
		Question quizz = new Question();
		quizz.setId((long) 123456789);
		quizz.setTitre("Trop bien !");

		OptionQuestion option = new OptionQuestion();
		option.setId((long) 12);
		option.setLibelle("LOL");
		option.setOk(false);

		List<OptionQuestion> list = new ArrayList<OptionQuestion>();
		list.add(option);

		quizz.setOptions(list);

		questionRepositoryJdbc.save(quizz);

		questionRepositoryJdbc.delete(quizz);
		// assertTrue(!questionRepositoryJdbc.getQuestions().contains(quizz));
	}

}
