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

import dev.paie.config.DataSourceMySQLConfig;
import dev.paie.config.JddConfig;
import dev.paie.config.ServicesConfig;
import dev.paie.entite.Grade;

@ContextConfiguration(classes = { DataSourceMySQLConfig.class, JddConfig.class, ServicesConfig.class })
@RunWith(SpringRunner.class)
public class GradeServiceJdbcTemplateTest {

	@Autowired
	private GradeService gradeService;

	@Autowired
	private Grade grade;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {

		gradeService.sauvegarder(grade);
		List<Grade> listeGrade = gradeService.lister();
		assertThat(listeGrade.get(0).getId(), equalTo(1));
		assertThat(listeGrade.get(0).getCode(), equalTo("code1"));
		assertThat(listeGrade.get(0).getNbHeuresBase(), equalTo(new BigDecimal("151.67")));
		assertThat(listeGrade.get(0).getTauxBase(), equalTo(new BigDecimal("11.10")));

		grade.setCode("code111");
		gradeService.mettreAJour(grade);
		listeGrade = gradeService.lister();
		assertThat(listeGrade.get(0).getCode(), equalTo("code111"));
	}
}
