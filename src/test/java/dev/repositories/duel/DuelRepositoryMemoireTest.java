package dev.repositories.duel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.entites.Duel;
import dev.entites.Quizz;

@ContextConfiguration(classes = { DuelRepositoryMemoire.class })
@RunWith(SpringRunner.class)
public class DuelRepositoryMemoireTest {

	@Autowired
	private DuelRepositoryMemoire service;

	@Test
	public void testFindAll() {
		assertThat(service.findAll().size()).isNotZero();
		assertThat(service.findAll().size()).isEqualTo(3);
	}

	@Test
	public void testSave() {
		assertThat(service.findAll().size()).isEqualTo(3);
		Duel newDuel = new Duel();
		newDuel.setId(999L); // donne un id pour ne pas faire planter testUpdate
								// plus tard
		service.save(newDuel);
		assertThat(service.findAll().size()).isEqualTo(4);
	}

	@Test
	public void testUpdate() {
		Duel oldDuel = service.findAll().get(0);
		assertThat(service.findAll().get(0).getQuizz().getTitre()).isEqualTo("Quizz 1");

		Quizz quizz = new Quizz();
		quizz.setTitre("Quizz sur la saucisse de Morteaux");

		oldDuel.setQuizz(quizz);
		service.update(oldDuel);
		assertThat(service.findAll().get(0).getQuizz().getTitre()).isEqualTo("Quizz sur la saucisse de Morteaux");
	}

	@Test
	public void testDelete() {
		assertThat(service.findAll().size()).isEqualTo(4);
		service.delete(service.findAll().get(1));
		assertThat(service.findAll().size()).isEqualTo(3);
	}

}
