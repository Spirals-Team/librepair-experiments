package dev.repositories.classe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.DataJpaTestConfig;
import dev.entites.Classe;
import dev.entites.Stagiaire;

@ContextConfiguration(classes = { ClasseRepositoryDataJpa.class, DataJpaTestConfig.class })
@RunWith(SpringRunner.class)
public class ClasseRepositoryDataJpaTest {

	@Autowired
	private ClasseRepository classeRepositoryDataJpa;

	@Test
	public void testFindAll() {
		List<Classe> classes = classeRepositoryDataJpa.findAll();
		assertThat(classes).isNotEmpty();
	}

	@Transactional
	@Test
	public void testSave() {
		List<Classe> classes = classeRepositoryDataJpa.findAll();
		int size = classes.size();
		Classe classe = new Classe("D15");
		List<Stagiaire> stagiaires = new ArrayList<>();
		Stagiaire st1 = new Stagiaire("clopin", "brigitte", "bribri@hotmail.fr",
				"https://www.valeursactuelles.com/sites/default/files/styles/image_article/public/2018-01/brigitte%20macron%20sipa.jpg?itok=0g8jrpff");
		stagiaires.add(st1);
		classe.setStagiaires(stagiaires);

		classeRepositoryDataJpa.save(classe);

		classes = classeRepositoryDataJpa.findAll();

		assertThat(classes.size()).isEqualTo(size + 1);
		assertThat(classes.get(size).getStagiaires().get(0).getPrenom()).isEqualTo("clopin");
	}

	@Transactional
	@Test
	public void testUpdate() {

		List<Classe> classes = classeRepositoryDataJpa.findAll();
		Classe c = classes.get(0);
		String nom = c.getNom();
		c.setNom("D15");
		classeRepositoryDataJpa.update(c);
		classes = classeRepositoryDataJpa.findAll();

		assertThat(classes.get(0).getNom()).isEqualTo("D15");
		assertThat(classes.get(0).getNom()).isNotEqualTo(nom);

	}

	@Transactional
	@Test
	public void delete() {
		List<Classe> classes = classeRepositoryDataJpa.findAll();
		int size = classes.size();
		Classe c1 = new Classe();
		for (Classe c : classes) {
			if (c.getNom().equals("d12-sans-stagiaires")) {
				c1 = c;
			}
		}

		Long id = c1.getId();

		classeRepositoryDataJpa.delete(c1);

		classes = classeRepositoryDataJpa.findAll();

		assertThat(classes.size()).isEqualTo(size - 1); //
		assertThat(classes.get(0).getId()).isNotEqualTo(id);
	}

	@Test
	public void findById() {
		Long id = 1L;
		Optional<Classe> classe = classeRepositoryDataJpa.findById(id);

		if (classe.isPresent()) {
			Classe classe2 = classe.get();

			assertThat(classe2).isNotNull();
			assertThat(classe2.getId()).isEqualTo(id);

		} else {
			fail();
		}
	}
}
