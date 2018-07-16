package dev.repositories.stagiaire;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.JpaTestConfig;
import dev.entites.Stagiaire;

@ContextConfiguration(classes = { StagiaireRepositoryJpa.class, JpaTestConfig.class })
@RunWith(SpringRunner.class)
public class StagiaireRepositoryJpaTest {

	@Autowired
	private StagiaireRepository stagiaireRepositoryJpa;

	@Test
	public void testFindAll() {
		List<Stagiaire> stagiaires = stagiaireRepositoryJpa.findAll();
		assertThat(stagiaires.isEmpty(), is(false));
	}

	@Test
	public void testSave() {
		Stagiaire staTest = new Stagiaire("prenomTest", "nomTest", "email@test.fr", "photoUrlTest");
		stagiaireRepositoryJpa.save(staTest);
		List<Stagiaire> stagiaires = stagiaireRepositoryJpa.findAll();
		assertThat(stagiaires.stream().filter(s -> s.getPrenom().equals("prenomTest")).findFirst().equals(staTest));
	}

	@Test
	public void testUpdate() {
		List<Stagiaire> stagiaires = stagiaireRepositoryJpa.findAll();
		Stagiaire s = stagiaireRepositoryJpa.findAll().get(0);
		s.setPrenom("Paul");
		stagiaireRepositoryJpa.update(s);
		List<Stagiaire> stagiaires2 = stagiaireRepositoryJpa.findAll();
		assertThat(stagiaires2.get(0).getPrenom()).isEqualTo("Paul");
	}

	@Test
	@Ignore
	public void testDelete() {
		List<Stagiaire> stagiaires = stagiaireRepositoryJpa.findAll();
		Stagiaire s = stagiaireRepositoryJpa.findAll().get(0);
		Long id = s.getId();
		stagiaireRepositoryJpa.delete(s);
		List<Stagiaire> stagiaires2 = stagiaireRepositoryJpa.findAll();
		assertThat(stagiaires2.stream().filter(s1 -> s1.getId().equals(id)).findFirst().isPresent(), is(false));
	}

}
