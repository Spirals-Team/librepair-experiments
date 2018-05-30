package dev.paie.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.ServicesConfig;
import dev.paie.entite.Grade;

@ContextConfiguration(classes = { ServicesConfig.class })

@RunWith(SpringRunner.class)
public class GradeServiceJdbcTemplateTest {

	@Autowired
	private GradeService gradeService;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		// TODO sauvegarder un nouveau grade
		Grade nouveauGrade = new Grade();
		nouveauGrade.setCode("M01");
		nouveauGrade.setNbHeuresBase(new BigDecimal("35.00"));
		nouveauGrade.setTauxBase(new BigDecimal("2.56"));
		gradeService.sauvegarder(nouveauGrade);

		// TODO vérifier qu'il est possible de récupérer le nouveau grade via la
		// méthode lister
		List<Grade> grades = gradeService.lister();
		Grade grade1 = grades.get(grades.size() - 1);
		assertThat(grade1.getCode(), equalTo("M01"));
		assertThat(grade1.getNbHeuresBase().compareTo(new BigDecimal("35")), equalTo(0));
		assertThat(grade1.getTauxBase().compareTo(new BigDecimal("3")), equalTo(0));

		// TODO modifier un grade
		Grade gradeModifie = grade1;
		gradeModifie.setCode("M02");
		gradeService.mettreAJour(gradeModifie);

		// TODO vérifier que les modifications sont bien prises en compte via la
		// méthode lister
		List<Grade> grades2 = gradeService.lister();
		Grade grade2 = grades2.get(grades2.size() - 1);
		assertThat(grade2.getCode(), equalTo("M02"));
		assertThat(grade2.getNbHeuresBase().compareTo(new BigDecimal("35")), equalTo(0));
		assertThat(grade2.getTauxBase().compareTo(new BigDecimal("3")), equalTo(0));
	}
}
