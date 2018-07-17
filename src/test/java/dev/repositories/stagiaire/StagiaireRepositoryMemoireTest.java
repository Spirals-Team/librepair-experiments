package dev.repositories.stagiaire;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.entites.Stagiaire;
import dev.repositories.RepositoryTestConfig;
import dev.repositories.stagiaire.StagiaireRepositoryMemoire;

//Sélection des classes de configuration Spring à utiliser lors du test
@ContextConfiguration(classes = { StagiaireRepositoryMemoire.class, RepositoryTestConfig.class })
// Configuration JUnit pour que Spring prenne la main sur le cycle de vie du
// test
@RunWith(SpringRunner.class)
public class StagiaireRepositoryMemoireTest {

	@Autowired
	private StagiaireRepositoryMemoire service;

	@Test
	public void testFindAll() {
		List<Stagiaire> stagiaires = service.findAll();
		assertThat(stagiaires.size()).isEqualTo(8);

	}

	@Test
	public void testSave() {
		assertThat(service.findAll().size()).isEqualTo(8);
		service.save(new Stagiaire());
		assertThat(service.findAll().size()).isEqualTo(9);
	}

	@Test
	public void testUpdate() {

		List<Stagiaire> stagiaires = service.findAll();
		Stagiaire s = service.findAll().get(0);
		s.setPrenom("Paul");
		service.update(s);
		assertThat(stagiaires.get(0).getPrenom()).isEqualTo("Paul");
	}

	@Test
	public void testDelete() {
		assertThat(service.findAll().size()).isEqualTo(9);
		Stagiaire s = service.findAll().get(0);
		service.delete(s);
		assertThat(service.findAll().size()).isEqualTo(8);
	}

	// tester toutes les méthodes du service

}
