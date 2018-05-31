package dev.paie.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.DataSourceMySQLConfig;
import dev.paie.config.JpaConfig;
import dev.paie.config.ServicesConfig;
import dev.paie.entite.Cotisation;

@ContextConfiguration(classes = { DataSourceMySQLConfig.class, JpaConfig.class, ServicesConfig.class })
@Repository
@RunWith(SpringRunner.class)
public class CotisationServiceJpaTest {

	@Autowired
	private CotisationService cotisationService;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		Cotisation cot = new Cotisation();
		cot.setCode("Code01");
		cot.setLibelle("libelle1");

		// TODO sauvegarder une nouvelle cotisation
		cotisationService.sauvegarder(cot);

		// TODO vérifier qu'il est possible de récupérer la nouvelle cotisation
		// via la méthode lister
		List<Cotisation> cotList = cotisationService.lister();
		for (Cotisation cotToCompare : cotList) {
			if (cotToCompare.getId().equals(cot.getId())) {
				assertThat(cotToCompare.getCode(), equalTo(cot.getCode()));
			}
		}

		cot.setCode("Code02");
		cot.setLibelle("Libelle2");
		// TODO modifier une cotisation
		cotisationService.mettreAJour(cot);

		// TODO vérifier que les modifications sont bien prises en compte via la
		// méthode lister
		cotList = cotisationService.lister();
		for (Cotisation cotToCompare : cotList) {
			if (cotToCompare.getId().equals(cot.getId())) {
				assertThat(cotToCompare.getCode(), equalTo(cot.getCode()));
			}
		}
	}

}
