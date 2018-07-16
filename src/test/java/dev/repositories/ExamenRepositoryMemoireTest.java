package dev.repositories;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.entites.Examen;
import dev.repositories.examen.ExamenBaseRepositoryMemoire;

//Sélection des classes de configuration Spring à utiliser lors du test
@ContextConfiguration(classes = { ExamenBaseRepositoryMemoire.class, RepositoryTestConfig.class })
// Configuration JUnit pour que Spring prenne la main sur le cycle de vie du
// test
@RunWith(SpringRunner.class)
public class ExamenRepositoryMemoireTest {

	@Autowired
	private ExamenBaseRepositoryMemoire examenService;

	@Ignore
	@Test
	public void testContext() {

		Examen examentest = examenService.findAll().get(0);

		assertTrue(examentest.getTitre().equals("Java EE"));
		assertTrue(examentest.getNotes().get(0).getNoteSur20() == 12);
		assertTrue(examentest.getClasse().getStagiaires().get(1).getEmail().equals("rourou@gmail.com"));

	}

	@Ignore
	@Test
	public void testSave() {

		Examen examToSave = new Examen();
		examToSave.setId(Long.parseLong("254"));

		examenService.save(examToSave);

		assertTrue(examenService.findAll().contains(examToSave));

	}

}
