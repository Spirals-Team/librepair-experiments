package dev.repositories.duel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.JpaTestConfig;
import dev.entites.Duel;
import dev.entites.Quizz;
import dev.entites.Stagiaire;

@ContextConfiguration(classes = { DuelRepositoryJpa.class, JpaTestConfig.class })
@RunWith(SpringRunner.class)
public class DuelRepositoryJpaTest {

	Stagiaire sA = null;
	Stagiaire sB = null;
	Quizz q = null;
	Duel d = null;

	@Autowired
	private DuelRepository duelRepositoryJpa;

	@Before
	public void setUp() throws Exception {
		sA = new Stagiaire();
		sA.setId(1L);
		sB = new Stagiaire();
		sB.setId(2L);
		q = new Quizz();
		q.setId(3L);
		d = new Duel(sA, sB, q);
		d.setId(1L);
	}

	@Test
	public void testFindAll() {
		assertThat(duelRepositoryJpa.findAll()).isNotEmpty();
	}

	@Test
	public void testSave() {
		assertThat(duelRepositoryJpa.findAll().size()).isEqualTo(9);
		duelRepositoryJpa.save(d);
		assertThat(duelRepositoryJpa.findAll().size()).isEqualTo(10);
	}

	@Test
	public void testUpdate() throws Exception {
		Duel oldDuel = duelRepositoryJpa.findAll().get(0);

		oldDuel.setStagiaireA(sA);
		oldDuel.setStagiaireB(sB);
		oldDuel.setQuizz(q);
		duelRepositoryJpa.update(oldDuel);

		assertThat(duelRepositoryJpa.findAll().get(0).getQuizz().getId()).isEqualTo(3L);
	}

	@Test
	public void testDelete() {
		assertThat(duelRepositoryJpa.findAll().size()).isEqualTo(10);
		duelRepositoryJpa.delete(duelRepositoryJpa.findAll().get(0));
		assertThat(duelRepositoryJpa.findAll().size()).isEqualTo(9);
	}

	@Test
	public void testFindById() throws Exception {
		Duel foundDuel = duelRepositoryJpa.findById(1L).orElseThrow(Exception::new);
		assertThat(foundDuel.getId()).isEqualTo(1L);
	}

}
