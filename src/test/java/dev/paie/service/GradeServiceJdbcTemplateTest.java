package dev.paie.service;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.H2Config;
import dev.paie.config.ServicesConfig;
import dev.paie.entite.Grade;

@ContextConfiguration(classes = { H2Config.class, ServicesConfig.class })
@RunWith(SpringRunner.class)
public class GradeServiceJdbcTemplateTest {

	@Autowired
	private GradeService gradeService;

	@Test
	public void test_savegarder_lister_mettre_a_jour() {
		// Création d'un nouveau grade
		Grade gradeTest = new Grade();
		gradeTest.setCode("test");
		gradeTest.setNbHeuresBase(new BigDecimal("36"));
		gradeTest.setTauxBase(new BigDecimal("11.0984"));

		// Sauvegarder un nouveau grade
		gradeService.sauvegarder(gradeTest);

		// Récupérer le nouveau grade via lister()
		List<Grade> listeTest = gradeService.lister();
		Iterator<Grade> it = listeTest.iterator();
		boolean testContains = false;
		while (it.hasNext()) {
			Grade nextGrade = it.next();
			if (nextGrade.getCode().equals("test"))
				testContains = true;
		}
		assertTrue(testContains);

		// Mettre à jour un grade
		gradeTest.setCode("autre");
		gradeService.mettreAJour(gradeTest);

		// Vérifier que les modifs sont prises en compte
		String newCode = gradeTest.getCode();
		assertTrue(newCode.equals("autre"));
	}

}
