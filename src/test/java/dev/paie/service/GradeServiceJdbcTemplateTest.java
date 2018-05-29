package dev.paie.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.DataSourceMySQLConfig;
import dev.paie.config.H2Config;
import dev.paie.config.JeuxDeDonneesConfig;
import dev.paie.config.ServicesConfig;
import dev.paie.entite.Grade;
import dev.paie.service.GradeService.GradeService;

//TODO compléter la configuration
@ContextConfiguration (classes = {GradeServiceJdbcTemplate.class,H2Config.class  ,JeuxDeDonneesConfig.class})
@RunWith(SpringRunner.class)
public class GradeServiceJdbcTemplateTest {

 @Autowired private GradeService gradeService;
 @Autowired private Grade grade;
 
 @Test
 public void test_sauvegarder_lister_mettre_a_jour() {
	  
     // TODO sauvegarder un nouveau grade
	 
	 gradeService.sauvegarder(grade);
     // TODO vérifier qu'il est possible de récupérer le nouveau grade via la méthode lister
	 List<Grade> lister = gradeService.lister();
	 assertThat(lister.contains(grade));
     // TODO modifier un grade
	 grade.setCode("test2");
	 gradeService.mettreAJour(grade);
     // TODO vérifier que les modifications sont bien prises en compte via la méthode lister
	 lister =gradeService.lister();
	 assertThat(lister.contains(grade));
	 
 }
}  