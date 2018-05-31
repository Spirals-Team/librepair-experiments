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
import dev.paie.config.JpaConfig;
import dev.paie.config.ServicesConfig;
import dev.paie.entite.Cotisation;

@ContextConfiguration(classes = { JpaConfig.class, H2Config.class, ServicesConfig.class })
@RunWith(SpringRunner.class)
public class CotisationServiceJpaTest {

	@Autowired
	private CotisationService cotisationService;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		// sauvegarder une nouvelle cotisation
		Cotisation cotisationTest = new Cotisation();
		cotisationTest.setCode("ABC");
		cotisationTest.setLibelle("test");
		cotisationTest.setTauxSalarial(new BigDecimal("1452.12"));
		cotisationTest.setTauxPatronal(new BigDecimal("3025.20"));

		cotisationService.sauvegarder(cotisationTest);

		// vérifier qu'il est possible de récupérer la nouvelle cotisation
		// via la méthode lister
		List<Cotisation> listeTest = cotisationService.lister();
		Iterator<Cotisation> it = listeTest.iterator();
		boolean testContains = false;
		while (it.hasNext()) {
			Cotisation nextCotisation = it.next();
			if (nextCotisation.getCode().equals("ABC"))
				testContains = true;
		}
		assertTrue(testContains);

		// modifier une cotisation
		cotisationTest.setLibelle("autre");
		cotisationService.mettreAJour(cotisationTest);

		// vérifier que les modifications sont bien prises en compte via la
		// méthode lister
		List<Cotisation> listeTest2 = cotisationService.lister();
		Iterator<Cotisation> it2 = listeTest2.iterator();
		boolean testContains2 = false;
		while (it2.hasNext()) {
			Cotisation nextCotisation = it2.next();
			if (nextCotisation.getLibelle().equals("autre"))
				testContains2 = true;
		}
		assertTrue(testContains2);

	}
}