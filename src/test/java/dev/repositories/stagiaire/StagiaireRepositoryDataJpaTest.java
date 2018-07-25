package dev.repositories.stagiaire;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.DataJpaTestConfig;
import dev.entites.Stagiaire;

@ContextConfiguration(classes = { StagiaireRepositoryDataJpa.class, DataJpaTestConfig.class })
@RunWith(SpringRunner.class)
public class StagiaireRepositoryDataJpaTest {

	@Autowired
	private StagiaireRepository stagiaireRpositoryDataJpa;

	@Test
	public void testFindAll() {
		List<Stagiaire> stagiaires = stagiaireRpositoryDataJpa.findAll();
		assertThat(stagiaires.isEmpty(), is(false));
	}

	@Test
	public void testSave() {
		Stagiaire staTest = new Stagiaire("prenomTest", "nomTest", "email@test.fr", "photoUrlTest");
		stagiaireRpositoryDataJpa.save(staTest);
		List<Stagiaire> stagiaires = stagiaireRpositoryDataJpa.findAll();
		assertThat(stagiaires.stream().filter(s -> s.getPrenom().equals("prenomTest")).findFirst().equals(staTest));
	}

	@Test
	public void testUpdate() {
		List<Stagiaire> stagiaires = stagiaireRpositoryDataJpa.findAll();
		Stagiaire s = stagiaireRpositoryDataJpa.findAll().get(0);
		s.setPrenom("Paul");
		stagiaireRpositoryDataJpa.update(s);
		List<Stagiaire> stagiaires2 = stagiaireRpositoryDataJpa.findAll();
		assertThat(stagiaires2.get(0).getPrenom()).isEqualTo("Paul");
	}

	@Test
	public void testDelete() {
		Stagiaire staTest = new Stagiaire("prenomTest", "nomTest", "email@test.fr", "photoUrlTest");
		stagiaireRpositoryDataJpa.save(staTest);
		Long id = staTest.getId();
		stagiaireRpositoryDataJpa.delete(staTest);
		Optional<Stagiaire> s1 = stagiaireRpositoryDataJpa.findById(id);
		assertThat(s1.isPresent(), is(false));
	}

	@Test
	public void testFindbyId() throws Exception {
		List<Stagiaire> stagiaires = stagiaireRpositoryDataJpa.findAll();
		Stagiaire s = stagiaireRpositoryDataJpa.findAll().get(0);
		Long id = s.getId();
		Stagiaire s2 = stagiaireRpositoryDataJpa.findById(id).orElseThrow(Exception::new);
		assertThat(s.getId()).isEqualTo(s2.getId());
		assertThat(s.getNom()).isEqualTo(s2.getNom());
	}
}
