package dev.paie.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.JeuxDeDonneesConfig;
import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.ResultatCalculRemuneration;
import dev.paie.util.PaieUtils;

//Sélection des classes de configuration Spring à utiliser lors du test
@ContextConfiguration(classes = { CalculerRemunerationServiceSimple.class, PaieUtils.class, JeuxDeDonneesConfig.class })
// Configuration JUnit pour que Spring prenne la main sur le cycle de vie du
// test

@RunWith(SpringRunner.class)
public class CalculerRemunerationServiceSimpleTest {

	@Autowired
	private CalculerRemunerationService remunerationService;

	@Autowired
	private BulletinSalaire bulletin;

	@Test
	public void test_calculer() {
		ResultatCalculRemuneration resultat = remunerationService.calculer(bulletin);
		assertThat(resultat.getSalaireBrut()).isEqualTo("2683.30");
		assertThat(resultat.getTotalRetenueSalarial()).isEqualTo("517.08");
		assertThat(resultat.getTotalCotisationsPatronales()).isEqualTo("1096.13");
		assertThat(resultat.getNetImposable()).isEqualTo("2166.22");
		assertThat(resultat.getNetAPayer()).isEqualTo("2088.41");
	}
}
