package dev.paie.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.DataSourceMySQLConfig;
import dev.paie.config.ServicesConfig;
import dev.paie.entite.Grade;

@ContextConfiguration(classes = { DataSourceMySQLConfig.class, ServicesConfig.class })
@Repository
@RunWith(SpringRunner.class)
public class GradeServiceJdbcTemplateTest {

	@Autowired
	private GradeService gradeService;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {

		Grade nouveauGrade = new Grade();
		nouveauGrade.setCode("Code01");
		nouveauGrade.setNbHeuresBase(new BigDecimal("120"));
		nouveauGrade.setTauxBase(new BigDecimal("12"));

		// TODO sauvegarder un nouveau grade
		gradeService.sauvegarder(nouveauGrade);

		// TODO vérifier qu'il est possible de récupérer le nouveau grade via la
		// méthode lister
		assertThat(gradeService.lister().get(0).getCode(), equalTo("Code01"));
		assertThat(gradeService.lister().get(0).getNbHeuresBase(), equalTo(new BigDecimal(120)));
		assertThat(gradeService.lister().get(0).getTauxBase(), equalTo(new BigDecimal(12)));

		// TODO modifier un grade
		nouveauGrade.setId(1);
		nouveauGrade.setCode("Code02");
		gradeService.mettreAJour(nouveauGrade);

		// TODO vérifier que les modifications sont bien prises en compte via la
		// méthode lister
		gradeService.lister();
		assertThat(gradeService.lister().get(0).getCode(), equalTo("Code02"));
	}

}
