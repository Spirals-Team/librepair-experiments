package dev.paie.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.H2Config;
import dev.paie.config.JddConfig;
import dev.paie.config.ServicesConfig;
import dev.paie.entite.Avantage;

@ContextConfiguration(classes = { ServicesConfig.class, JddConfig.class, H2Config.class })
@RunWith(SpringRunner.class)
public class AvantageRepositoryTest {
	
	@Autowired
	private Avantage avantage;

	@Autowired
	private AvantageRepository avantageRepository;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		System.out.println(avantage.getId());
		
		// TODO sauvegarder un nouvel avantage
		avantageRepository.save(avantage);

		// TODO vérifier qu'il est possible de récupérer le nouvel avantage via
		// la méthode findOne
		Avantage av = avantageRepository.findOne(avantage.getId());
		assertThat(avantage.equals(av));
		
		// TODO modifier un avantage
		Avantage avantageUpdate = avantageRepository.findOne(avantage.getId());
		avantageUpdate.setCode("789");
		
		// TODO vérifier que les modifications sont bien prises en compte via la
		// méthode findOne
		
		Avantage avVerif = avantageRepository.findOne(avantageUpdate.getId());
		assertThat(avantage.equals(avVerif));
	}
}
