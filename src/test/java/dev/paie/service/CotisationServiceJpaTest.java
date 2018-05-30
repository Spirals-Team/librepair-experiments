package dev.paie.service;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.JPAConfig;
import dev.paie.config.ServiceConfig;
import dev.paie.entite.Cotisation;

@ContextConfiguration(classes = { ServiceConfig.class, JPAConfig.class })

@RunWith(SpringRunner.class)

public class CotisationServiceJpaTest {
	@Autowired
	private CotisationService cotisationService;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		// TODO sauvegarder une nouvelle cotisation
		Cotisation cot = new Cotisation();
		cot.setCode("pat");
		cot.setLibelle("cotisation patron");
		cot.setTauxPatronal(new BigDecimal("20"));
		cot.setTauxSalarial(new BigDecimal("10"));

		cotisationService.sauvegarder(cot);
		// TODO vérifier qu'il est possible de récupérer la nouvelle cotisation
		// via la méthode lister
		List<Cotisation> maBDD = cotisationService.list();

		for (Cotisation cotisation : maBDD) {
			System.out.println(cotisation.getLibelle());
		}
		// TODO modifier une cotisation
		cot.setLibelle("cotisation fait par les patrons");
		cotisationService.mettreAJour(cot);
		// TODO vérifier que les modifications sont bien prises en compte via la
		// méthode lister
		maBDD = cotisationService.list();

		for (Cotisation cotisation : maBDD) {
			System.out.println(cotisation.getLibelle());
		}

	}
}
