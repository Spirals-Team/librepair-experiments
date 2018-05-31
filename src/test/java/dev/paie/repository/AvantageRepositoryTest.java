package dev.paie.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.H2Config;
import dev.paie.config.JpaConfig;
import dev.paie.config.ServicesConfig;
import dev.paie.entite.Avantage;

@ContextConfiguration(classes = { JpaConfig.class, H2Config.class, ServicesConfig.class })
@RunWith(SpringRunner.class)
public class AvantageRepositoryTest {

	@Autowired
	private AvantageRepository avantageRepository;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		// sauvegarder un nouvel avantage
		Avantage avantageTest = new Avantage();
		avantageTest.setCode("ABC");
		avantageTest.setMontant(new BigDecimal("2563.23"));
		avantageTest.setNom("test");
		avantageRepository.save(avantageTest);

		// vérifier qu'il est possible de récupérer le nouvel avantage via
		// la méthode findOne
		assertTrue("test".equals(avantageRepository.findOne(avantageTest.getId()).getNom()));

		// modifier un avantage
		avantageTest.setNom("autre");
		avantageRepository.save(avantageTest);

		// vérifier que les modifications sont bien prises en compte via la
		// méthode findOne
		assertEquals("autre", avantageRepository.findOne(avantageTest.getId()).getNom());
	}

	@Test
	public void test_recherche_par_code() {
		List<Avantage> listA = avantageRepository.findByCode("ABC");
		Avantage avantage = listA.iterator().next();
		assertEquals("ABC", avantage.getCode());
	}
}