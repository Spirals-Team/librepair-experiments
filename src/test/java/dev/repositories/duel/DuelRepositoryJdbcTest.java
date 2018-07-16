package dev.repositories.duel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.DataSourceTestConfig;
import dev.entites.Duel;
import dev.entites.Quizz;
import dev.entites.Stagiaire;

@ContextConfiguration(classes = { DuelRepositoryJdbc.class, DataSourceTestConfig.class })
@RunWith(SpringRunner.class)
public class DuelRepositoryJdbcTest {

	Stagiaire sA = null;
	Stagiaire sB = null;
	Quizz q = null;
	Duel d = null;

	@Autowired
	private DuelRepositoryJdbc duelRepositoryJdbc;

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
		assertThat(duelRepositoryJdbc.findAll().isEmpty()).isFalse();
	}

	@Test
	public void testSave() {
		assertThat(duelRepositoryJdbc.findAll().size()).isEqualTo(9);
		duelRepositoryJdbc.save(d);
		assertThat(duelRepositoryJdbc.findAll().size()).isEqualTo(10);
	}

	@Test
	public void testUpdate() throws Exception {
		Duel oldDuel = duelRepositoryJdbc.findAll().get(0);

		oldDuel.setStagiaireA(sA);
		oldDuel.setStagiaireB(sB);
		oldDuel.setQuizz(q);
		duelRepositoryJdbc.update(oldDuel);

		assertThat(duelRepositoryJdbc.findAll().get(0).getQuizz().getId()).isEqualTo(3L);
	}

	@Test
	public void testDelete() {
		assertThat(duelRepositoryJdbc.findAll().size()).isEqualTo(10);
		duelRepositoryJdbc.delete(duelRepositoryJdbc.findAll().get(0));
		assertThat(duelRepositoryJdbc.findAll().size()).isEqualTo(9);
	}

	@Test
	public void testFindById() throws Exception {
		Duel foundDuel = duelRepositoryJdbc.findById(1L).orElseThrow(Exception::new);
		assertThat(foundDuel.getId()).isEqualTo(1L);
	}

}
