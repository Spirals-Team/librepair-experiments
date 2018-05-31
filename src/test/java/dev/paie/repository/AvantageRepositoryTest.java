package dev.paie.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.DataSourceMySQLConfig;
import dev.paie.config.JpaConfig;
import dev.paie.config.ServicesConfig;
import dev.paie.entite.Avantage;

@ContextConfiguration(classes = { DataSourceMySQLConfig.class, JpaConfig.class, ServicesConfig.class })
@Repository
@RunWith(SpringRunner.class)
public class AvantageRepositoryTest {

	@Autowired
	private AvantageRepository avantageRepository;

	@Autowired
	private Avantage avantage;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		// TODO sauvegarder un nouvel avantage
		avantageRepository.saveAndFlush(avantage);

		// TODO vérifier qu'il est possible de récupérer le nouvel avantage via
		// la méthode findOne
		assertThat(avantageRepository.findOne(avantage.getId()), notNullValue());

		// TODO modifier un avantage
		avantage.setCode("Code22");
		avantage.setNom("Avantage22");
		avantage.setMontant(new BigDecimal(500));
		avantageRepository.save(avantage);

		// TODO vérifier que les modifications sont bien prises en compte via la
		// méthode findOne
		assertThat(avantageRepository.findOne(avantage.getId()).getCode(), equalTo("Code22"));
	}

}
