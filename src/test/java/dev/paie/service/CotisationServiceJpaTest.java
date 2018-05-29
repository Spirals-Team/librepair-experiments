package dev.paie.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.H2Config;
import dev.paie.config.JeuxDeDonneesConfig;
import dev.paie.config.JpaConfig;
import dev.paie.config.ServicesConfig;
import dev.paie.entite.Cotisation;

import dev.paie.util.PaieUtils;
//Sélection des classes de configuration Spring à utiliser lors du test
@ContextConfiguration(classes = {ServicesConfig.class  ,JeuxDeDonneesConfig.class, H2Config.class}) 
//Configuration JUnit pour que Spring prenne la main sur le cycle de vie du test
@RunWith(SpringRunner.class)
public class CotisationServiceJpaTest {
	 @Autowired private CotisationService cotisationService;

	 @Autowired 
	 @Qualifier("e900")
	 private Cotisation cotisation ;

	 	@Test
	    public void test_sauvegarder_lister_mettre_a_jour() {
	        // TODO sauvegarder une nouvelle cotisation
	 		cotisationService.sauvegarder(cotisation);
	        // TODO vérifier qu'il est possible de récupérer la nouvelle cotisation via la méthode lister
	 		 assertThat(cotisationService.lister().contains(cotisation));
	        // TODO modifier une cotisation
	 		 cotisation.setCode("set");
	        // TODO vérifier que les modifications sont bien prises en compte via la méthode lister
	 		cotisationService.mettreAJour(cotisation);
	 		assertThat(cotisationService.lister().contains(cotisation));
	    }
}
