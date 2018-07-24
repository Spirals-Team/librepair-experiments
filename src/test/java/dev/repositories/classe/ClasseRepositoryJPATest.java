package dev.repositories.classe;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.JpaTestConfig;
import dev.entites.Classe;
import dev.entites.Stagiaire;

@ContextConfiguration(classes = { ClasseRepositoryJPA.class, JpaTestConfig.class })
@RunWith(SpringRunner.class)
public class ClasseRepositoryJPATest {

	@Autowired
	ClasseRepository classeRepositoryJPA;

	@Test
	public void testFindAll() {
		List<Classe> classes = classeRepositoryJPA.findAll();
		assertThat(classes).isNotEmpty();
	}

	@Test
	public void testSave() {
		List<Classe> classes = classeRepositoryJPA.findAll();
		int size = classes.size();
		Classe classe = new Classe("D15");
		List<Stagiaire> stagiaires = new ArrayList<>();
		Stagiaire st1 = new Stagiaire("clopin", "brigitte", "bribri@hotmail.fr",
				"https://www.valeursactuelles.com/sites/default/files/styles/image_article/public/2018-01/brigitte%20macron%20sipa.jpg?itok=0g8jrpff");
		stagiaires.add(st1);
		classe.setStagiaires(stagiaires);
		classeRepositoryJPA.save(classe);
		classes = classeRepositoryJPA.findAll();
		assertThat(classes.size()).isEqualTo(size + 1);
	}

	@Test
	public void testUpdate() {
		List<Classe> classes = classeRepositoryJPA.findAll();
		Classe c = classes.get(0);
		String nom = c.getNom();
		c.setNom("D15");
		classeRepositoryJPA.update(c);
		classes = classeRepositoryJPA.findAll();

		assertThat(classes.get(0).getNom()).isEqualTo("D15");
		assertThat(classes.get(0).getNom()).isNotEqualTo(nom);
	}

	@Test
	public void delete() {
		List<Classe> classes = classeRepositoryJPA.findAll();
		int size = classes.size();
		Classe c1 = new Classe();
		for (Classe c : classes) {
			if (c.getNom().equals("d12-sans-stagiaires")) {
				c1 = c;
			}
		}

		Long id = c1.getId();

		classeRepositoryJPA.delete(c1);

		classes = classeRepositoryJPA.findAll();

		assertThat(classes.size()).isEqualTo(size - 1); //
		assertThat(classes.get(0).getId()).isNotEqualTo(id);
	}

	/*
	 *
	 * @Test public Optional<Classe> findById() { return null; }
	 */

}
