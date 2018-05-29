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

import dev.paie.config.ServicesConfig;
import dev.paie.entite.Cotisation;

@ContextConfiguration(classes = { ServicesConfig.class })

@RunWith(SpringRunner.class)
public class CotisationServiceJpaTest {

	@Autowired
	private CotisationService cotisationService;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		// TODO sauvegarder une nouvelle cotisation
		Cotisation cotisation = new Cotisation();
		cotisation.setCode("test1");
		cotisation.setLibelle("taxe sur la valeur non ajoutée");
		cotisation.setTauxPatronal(new BigDecimal("32.00"));
		cotisation.setTauxSalarial(new BigDecimal("14.00"));
		cotisationService.sauvegarder(cotisation);

		// TODO vérifier qu'il est possible de récupérer la nouvelle cotisation
		// via la méthode lister
		List<Cotisation> listCotisations1 = cotisationService.lister();
		Cotisation cotisation1 = listCotisations1.get(listCotisations1.size() - 1);
		assertThat(cotisation1.getCode(), equalTo("test1"));
		assertThat(cotisation1.getLibelle(), equalTo("taxe sur la valeur non ajoutée"));
		assertThat(cotisation1.getTauxPatronal().compareTo(new BigDecimal("32")), equalTo(0));
		assertThat(cotisation1.getTauxSalarial().compareTo(new BigDecimal("14")), equalTo(0));

		// TODO modifier une cotisation
		Cotisation cotisationModifie = cotisation1;
		cotisationModifie.setCode("test2");
		cotisationModifie.setLibelle("taxe sur la valeur ajoutée");
		cotisationService.mettreAJour(cotisationModifie);

		// TODO vérifier que les modifications sont bien prises en compte via la
		// méthode lister
		List<Cotisation> listCotisations2 = cotisationService.lister();
		Cotisation cotisation2 = listCotisations2.get(listCotisations2.size() - 1);
		assertThat(cotisation2.getCode(), equalTo("test2"));
		assertThat(cotisation2.getLibelle(), equalTo("taxe sur la valeur ajoutée"));
		assertThat(cotisation2.getTauxPatronal().compareTo(new BigDecimal("32")), equalTo(0));
		assertThat(cotisation2.getTauxSalarial().compareTo(new BigDecimal("14")), equalTo(0));
	}
}