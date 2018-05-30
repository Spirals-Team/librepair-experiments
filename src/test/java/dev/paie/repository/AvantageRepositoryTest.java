package dev.paie.repository;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.JPAConfig;
import dev.paie.config.ServiceConfig;
import dev.paie.entite.Avantage;

@ContextConfiguration(classes = { ServiceConfig.class, JPAConfig.class })

@RunWith(SpringRunner.class)

public class AvantageRepositoryTest {

	@Autowired
	private AvantageRepository avantageRepository;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		// TODO sauvegarder un nouvel avantage
		Avantage nouvelleAvantage = new Avantage();
		nouvelleAvantage.setCode("nouveau");
		nouvelleAvantage.setNom("primeArrivee");
		nouvelleAvantage.setMontant(new BigDecimal("200"));
		avantageRepository.save(nouvelleAvantage);
		// TODO vérifier qu'il est possible de récupérer le nouvel avantage via
		// la méthode findOne

		Avantage avantage = avantageRepository.findOne(6);

		System.out.println(avantage.getNom());

		// TODO modifier un avantage
		avantage.setCode("JDR");
		avantageRepository.save(avantage);
		// TODO vérifier que les modifications sont bien prises en compte via la
		// méthode findOne
		avantage = avantageRepository.findOne(6);

		System.out.println(avantage.getCode());
	}
}
