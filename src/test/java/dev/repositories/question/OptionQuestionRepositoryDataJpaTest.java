package dev.repositories.question;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.DataJpaTestConfig;
import dev.entites.OptionQuestion;

@ContextConfiguration(classes = { OptionQuestionRepositoryDataJpa.class, DataJpaTestConfig.class })
@RunWith(SpringRunner.class)
public class OptionQuestionRepositoryDataJpaTest {

	@Autowired
	private OptionQuestionRepository optionQuestionRepositoryDataJpa;

	// TODO créer les cas de test

	@Test
	public void test_FindAll() {
		assertTrue(!optionQuestionRepositoryDataJpa.findAll().isEmpty());
	}

	@Test
	public void test_FindById() {
		Optional<OptionQuestion> test = optionQuestionRepositoryDataJpa.findById((long) 1);
		assertTrue(test.get().getLibelle().equals("1"));
		assertTrue(!test.get().getOk());
	}

	@Test
	public void test_Save() {
		OptionQuestion option = new OptionQuestion();
		option.setLibelle("LOL");
		option.setOk(false);
		optionQuestionRepositoryDataJpa.save(option);
		List<OptionQuestion> maListe = optionQuestionRepositoryDataJpa.findAll();

		assertTrue(maListe.get(3).getLibelle().equals("LOL"));
		assertTrue(!maListe.get(3).getOk());
	}

	@Test
	public void test_Update() {
		OptionQuestion option = new OptionQuestion();
		option.setId((long) 1);
		option.setLibelle("UN");
		option.setOk(true);

		optionQuestionRepositoryDataJpa.update(option);

		OptionQuestion test = optionQuestionRepositoryDataJpa.findById((long) 1).get();

		System.out.println("test libelle = " + test.getLibelle() + " ok = " + test.getOk());

		assertTrue(test.getLibelle().equals("UN"));
		assertTrue(test.getOk());

	}

	@Test
	public void test_Delete() {
		OptionQuestion option = new OptionQuestion();
		option.setId((long) 12);
		option.setLibelle("LOL");
		option.setOk(false);
		optionQuestionRepositoryDataJpa.save(option);
		optionQuestionRepositoryDataJpa.delete(option);
		assertTrue(!optionQuestionRepositoryDataJpa.findAll().contains(option));
	}

}
