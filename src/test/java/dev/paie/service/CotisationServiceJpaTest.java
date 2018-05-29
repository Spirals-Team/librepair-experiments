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

import dev.paie.config.H2Config;
import dev.paie.config.JpaConfig;
import dev.paie.entite.Cotisation;
import dev.paie.util.PaieUtils;

@ContextConfiguration(classes = { H2Config.class, JpaConfig.class, PaieUtils.class, CotisationServiceJpa.class })
@RunWith(SpringRunner.class)
public class CotisationServiceJpaTest {

	@Autowired
	private CotisationService cotisationService;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {

		Cotisation cotisation = new Cotisation();
		cotisation.setId(null);
		cotisation.setCode("FR");
		cotisation.setLibelle("AAA");
		cotisation.setTauxPatronal(new BigDecimal("15.87"));
		cotisation.setTauxSalarial(new BigDecimal("1.21"));
		cotisationService.sauvegarder(cotisation);

		List<Cotisation> liste = cotisationService.lister();
		assertThat(liste.get(0).getId(), equalTo(1));
		assertThat(liste.get(0).getCode(), equalTo("FR"));
		assertThat(liste.get(0).getLibelle(), equalTo("AAA"));
		assertThat(liste.get(0).getTauxPatronal(), equalTo(new BigDecimal("15.87")));
		assertThat(liste.get(0).getTauxSalarial(), equalTo(new BigDecimal("1.21")));

		cotisation.setCode("EN");
		cotisationService.mettreAJour(cotisation);
		liste = cotisationService.lister();
		assertThat(liste.get(0).getCode(), equalTo("EN"));

	}
}
