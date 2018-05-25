package dev.paie.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.DataSourceMySQLConfig;
import dev.paie.entite.Grade;

@Configuration
@ImportResource("classpath:jdd-config.xml")
@ContextConfiguration(classes = { DataSourceMySQLConfig.class })
@RunWith(SpringRunner.class)
public class GradeServiceJdbcTemplateTest {

	@Autowired
	private GradeService gradeService;

	@Autowired
	@Qualifier("grade1")
	private Grade grade;

	@Test
	public void test_sauvegarder_liste_mettre_a_jour() {

		// TODO sauvegarder un nouveau grade
		try {
			gradeService.sauvegarder(grade);
		} catch (DataAccessException ex) {
			fail();
		}

		// TODO vérifier qu'il est possible de récupérer le nouveau grade via la
		// méthode lister
		try {
			List<Grade> listeGrade = gradeService.lister();
			assertThat(listeGrade.contains(grade));
		} catch (DataAccessException ex) {
			fail();
		}

		// TODO modifier un grade
		try {
			grade.setCode("456");
			gradeService.mettreAJour(grade);
		} catch (DataAccessException ex) {
			fail();
		}

		// TODO vérifier que les modifications sont bien prises en compte via la
		// méthode lister
		try {
			List<Grade> listeGradeUpdate = gradeService.lister();
			assertThat(listeGradeUpdate.contains(grade));
		} catch (DataAccessException ex) {
			fail();
		}
	}
}
