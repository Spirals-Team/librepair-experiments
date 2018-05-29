package dev.paie.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.DataSourceMySQLConfig;
import dev.paie.config.JddConfig;
import dev.paie.config.JpaConfig;
import dev.paie.config.ServicesConfig;
import dev.paie.entite.Avantage;

@ContextConfiguration(classes = { AvantageRepository.class, DataSourceMySQLConfig.class, JpaConfig.class,
		JddConfig.class, ServicesConfig.class })
@RunWith(SpringRunner.class)
public class AvantageRepositoryTest {

	@Autowired
	private AvantageRepository avantageRepository;

	@Autowired
	private Avantage av;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		// TODO sauvegarder un nouvel avantage
		avantageRepository.save(av);

		// TODO vérifier qu'il est possible de récupérer le nouvel avantage via
		// la méthode findOne
		assertThat(avantageRepository.findOne(av.getId()), notNullValue());

		// TODO modifier un avantage
		av.setCode("code2");
		avantageRepository.save(av);

		// TODO vérifier que les modifications sont bien prises en compte via la
		// méthode findOne
		assertThat(avantageRepository.findOne(av.getId()).getCode(), equalTo("code2"));
	}
}
