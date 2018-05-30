package dev.paie.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.ServiceConfig;
import dev.paie.entite.Grade;

@ContextConfiguration(classes = { ServiceConfig.class })

@RunWith(SpringRunner.class)
public class GradeServiceJdbcTemplateTest {
	@Autowired
	private GradeService gradeService;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		// sauvegarder un nouveau grade
		Grade nouveau = new Grade();
		nouveau.setId(20);
		nouveau.setCode("info");
		nouveau.setNbHeuresBase(new BigDecimal("34"));
		nouveau.setTauxBase(new BigDecimal("20"));

		gradeService.sauvegarder(nouveau);
		// TODO vérifier qu'il est possible de récupérer le nouveau grade via la
		// méthode lister
		List<Grade> maBDD = gradeService.lister();

		for (Grade grade : maBDD) {
			System.out.println(grade.getCode());
		}
		// TODO modifier un grade

		nouveau.setCode("responsable de niveau");

		gradeService.mettreAJour(nouveau);

		maBDD = gradeService.lister();
		// TODO vérifier que les modifications sont bien prises en compte via la
		// méthode lister
		for (Grade grade : maBDD) {
			System.out.println(grade.getCode());
		}

		assertTrue(maBDD.size() == 2);
		assertTrue(maBDD.get(1).getCode().equals("responsable de niveau"));
		assertThat(maBDD.get(1).getNbHeuresBase().compareTo(new BigDecimal("34")), equalTo(0));
		assertThat(maBDD.get(1).getTauxBase().compareTo(new BigDecimal("20")), equalTo(0));
	}
}
