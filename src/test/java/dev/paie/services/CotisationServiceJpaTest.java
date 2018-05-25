package dev.paie.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.DataSourceMySQLConfig;
import dev.paie.config.JddConfig;
import dev.paie.config.JpaConfig;
import dev.paie.config.ServicesConfig;
import dev.paie.entite.Cotisation;

@ContextConfiguration(classes = { DataSourceMySQLConfig.class, JpaConfig.class, JddConfig.class, ServicesConfig.class })
@RunWith(SpringRunner.class)
public class CotisationServiceJpaTest {

	@Autowired
	@Qualifier("cot1")
	private Cotisation cot;

	@Autowired
	private CotisationService cotisationService;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		// TODO sauvegarder une nouvelle cotisation
		cotisationService.sauvegarder(cot);

		// TODO vérifier qu'il est possible de récupérer la nouvelle cotisation
		// via la méthode lister
		List<Cotisation> cots = cotisationService.lister();
		assertThat(cots.get(0).getCode(), equalTo("code1"));

		// TODO modifier une cotisation
		cot.setId(2);
		cot.setCode("code2");
		cotisationService.mettreAJour(cot);
		cots = cotisationService.lister();
		assertThat(cots.get(1).getCode(), equalTo("code2"));
		// TODO vérifier que les modifications sont bien prises en compte via la
		// méthode lister
	}
}
