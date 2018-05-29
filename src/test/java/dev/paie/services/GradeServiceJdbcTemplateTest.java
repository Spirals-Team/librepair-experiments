package dev.paie.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.DataSourceMySQLConfig;
import dev.paie.config.JddConfig;
import dev.paie.config.ServicesConfig;
import dev.paie.entite.Grade;

@ContextConfiguration(classes = { DataSourceMySQLConfig.class, JddConfig.class, ServicesConfig.class })
// TODO compléter la configuration
@RunWith(SpringRunner.class)
public class GradeServiceJdbcTemplateTest {

	@Autowired
	private GradeService gradeService;

	@Autowired
	private Grade grade;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {

		// TODO sauvegarder un nouveau grade
		gradeService.sauvegarder(grade);

		// TODO vérifier qu'il est possible de récupérer le nouveau grade via la
		// méthode lister
		List<Grade> grades = gradeService.lister();

		grade.setId(1);
		assertThat(grades.get(1).getCode(), equalTo("code1"));

		// TODO modifier un grade
		grade.setCode("code2");
		gradeService.mettreAJour(grade);

		// TODO vérifier que les modifications sont bien prises en compte via la
		// méthode lister
		grades = gradeService.lister();
		assertThat(grades.get(0).getCode(), equalTo("code2"));
	}
}
