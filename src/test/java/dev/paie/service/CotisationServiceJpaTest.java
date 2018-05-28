package dev.paie.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.H2Config;
import dev.paie.config.JpaConfig;
import dev.paie.entite.Cotisation;

@ContextConfiguration(classes = { CotisationServiceJpa.class, JpaConfig.class, H2Config.class })
@RunWith(SpringRunner.class)
public class CotisationServiceJpaTest {

	@Autowired
	private CotisationService cotisationService;

	private Cotisation cotisation;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {

		cotisation = new Cotisation(new Integer(1), "EP50", "l", new BigDecimal("0.54045"), new BigDecimal("0.1478"));

		cotisationService.supprimer();

		cotisationService.sauvegarder(cotisation);

		assertThat(cotisationService.lister().contains(cotisation));

		cotisation.setCode("EP00");

		cotisationService.mettreAJour(cotisation);

		assertThat(cotisationService.lister().contains(cotisation));
	}
}