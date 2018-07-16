package dev.repositories.question;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.entites.OptionQuestion;
import dev.entites.Question;
import dev.repositories.question.QuestionRepositoryMemoire;

//Sélection des classes de configuration Spring à utiliser lors du test
@ContextConfiguration(classes = { QuestionRepositoryMemoire.class })
// Configuration JUnit pour que Spring prenne la main sur le cycle de vie du
// test
@RunWith(SpringRunner.class)
public class QuestionRepositoryMemoireTest {

	@Autowired
	private QuestionRepositoryMemoire service;

	@Test
	public void test_Initialiser() {
		service.initialiser();
		assertNotNull(service);
		assertEquals("ver 1.0", service.getVersion());
	}

	@Test
	public void test_FindAll() {
		assertTrue(!service.findAll().isEmpty());
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

		service.save(quizz);

		assertTrue(service.getQuestions().contains(quizz));
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

		service.save(quizz);

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

		service.update(quizz2);

		int id = service.getQuestions().indexOf(quizz2);

		assertTrue(service.getQuestions().get(id).getTitre().equals("Trop bien ?"));
		assertTrue(service.getQuestions().get(id).getOptions().get(0).getId().equals((long) 12));
		assertTrue(service.getQuestions().get(id).getOptions().get(0).getOk());
		assertTrue(service.getQuestions().get(id).getOptions().get(0).getLibelle().equals("Pas LOL"));

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

		service.save(quizz);

		service.delete(quizz);
		assertTrue(!service.getQuestions().contains(quizz));
	}

}
