package dev.repositories.question;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.entites.OptionQuestion;
import dev.repositories.question.OptionQuestionRepositoryMemoire;

//Sélection des classes de configuration Spring à utiliser lors du test
@ContextConfiguration(classes = { OptionQuestionRepositoryMemoire.class })
// Configuration JUnit pour que Spring prenne la main sur le cycle de vie du
// test
@RunWith(SpringRunner.class)
public class OptionQuestionRepositoryMemoireTest {

	@Autowired
	private OptionQuestionRepositoryMemoire service;

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
		OptionQuestion option = new OptionQuestion();
		option.setId((long) 12);
		option.setLibelle("LOL");
		option.setOk(false);
		service.save(option);
		assertTrue(service.getOptionQuestions().contains(option));
	}

	@Test
	public void test_Update() {
		OptionQuestion option = new OptionQuestion();
		option.setId((long) 12);
		option.setLibelle("LOL");
		option.setOk(false);
		service.save(option);

		OptionQuestion newOption = new OptionQuestion();
		newOption.setId((long) 12);
		newOption.setLibelle("Pas LOL");
		newOption.setOk(true);
		service.update(newOption);

		int id = service.getOptionQuestions().indexOf(newOption);

		assertTrue(service.getOptionQuestions().get(id).getLibelle().equals("Pas LOL")
				&& service.getOptionQuestions().get(id).getOk());

	}

	@Test
	public void test_Delete() {
		OptionQuestion option = new OptionQuestion();
		option.setId((long) 12);
		option.setLibelle("LOL");
		option.setOk(false);
		service.save(option);
		service.delete(option);
		assertTrue(!service.getOptionQuestions().contains(option));
	}

}
