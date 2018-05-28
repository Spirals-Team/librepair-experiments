package dev.paie.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.H2Config;
import dev.paie.config.JeuxDeDonneesConfig;
import dev.paie.entite.Grade;

@ContextConfiguration(classes = { GradeServiceJdbcTemplate.class, H2Config.class, JeuxDeDonneesConfig.class })
@RunWith(SpringRunner.class)
public class GradeServiceJdbcTemplateTest {

	@Autowired
	private GradeService gradeService;

	@Autowired
	private Grade grade1;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {

		gradeService.supprimer();

		gradeService.sauvegarder(grade1);

		assertThat(gradeService.lister().contains(grade1));

		grade1.setCode("G1");

		gradeService.mettreAJour(grade1);

		assertThat(gradeService.lister().contains(grade1));

	}
}
