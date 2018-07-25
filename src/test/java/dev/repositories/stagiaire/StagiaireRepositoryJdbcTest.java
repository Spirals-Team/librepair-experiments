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

import dev.config.DataSourceTestConfig;
import dev.entites.Stagiaire;

@ContextConfiguration(classes = { StagiaireRepositoryJdbc.class, DataSourceTestConfig.class })
@RunWith(SpringRunner.class)
public class StagiaireRepositoryJdbcTest {

	@Autowired
	private StagiaireRepositoryJdbc stagiaireRepositoryJdbc;

	@Test
	public void testFindAll() {
		List<Stagiaire> stagiaires = stagiaireRepositoryJdbc.findAll();
		assertThat(stagiaires.isEmpty(), is(false));
	}

	@Ignore
	@Test
	public void testSave() {
		Stagiaire staTest = new Stagiaire("prenomTest", "nomTest", "emailTest", "photoUrlTest");
		stagiaireRepositoryJdbc.save(staTest);
		List<Stagiaire> stagiaires = stagiaireRepositoryJdbc.findAll();
		assertThat(stagiaires.stream().filter(s -> s.getPrenom().equals("prenomTest")).findFirst().equals(staTest));
	}

	@Test
	public void testUpdate() {
		List<Stagiaire> stagiaires = stagiaireRepositoryJdbc.findAll();
		Stagiaire s = stagiaireRepositoryJdbc.findAll().get(0);
		s.setPrenom("Paul");
		stagiaireRepositoryJdbc.update(s);
		List<Stagiaire> stagiaires2 = stagiaireRepositoryJdbc.findAll();
		assertThat(stagiaires2.get(0).getPrenom()).isEqualTo("Paul");
	}

	@Test
	@Ignore
	public void testDelete() {
		List<Stagiaire> stagiaires = stagiaireRepositoryJdbc.findAll();
		Stagiaire s = stagiaireRepositoryJdbc.findAll().get(0);
		Long id = s.getId();
		stagiaireRepositoryJdbc.delete(s);
		List<Stagiaire> stagiaires2 = stagiaireRepositoryJdbc.findAll();
		assertThat(stagiaires2.stream().filter(s1 -> s1.getId().equals(id)).findFirst().isPresent(), is(false));
	}
}
