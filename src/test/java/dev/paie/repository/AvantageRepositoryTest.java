package dev.paie.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.H2Config;
import dev.paie.config.JpaConfig;
import dev.paie.config.ServicesConfig;
import dev.paie.entite.Avantage;

@ContextConfiguration(classes = { H2Config.class, JpaConfig.class, ServicesConfig.class })
@RunWith(SpringRunner.class)
public class AvantageRepositoryTest {

	@Autowired
	private AvantageRepository avantageRepository;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {

		Avantage av = new Avantage(1, "FR", "BOURDAUD", new BigDecimal("1430.02"));
		avantageRepository.save(av);

		assertThat(avantageRepository.findOne(1).getId(), equalTo(1));
		assertThat(avantageRepository.findOne(1).getCode(), equalTo("FR"));
		assertThat(avantageRepository.findOne(1).getNom(), equalTo("BOURDAUD"));
		assertThat(avantageRepository.findOne(1).getMontant(), equalTo(new BigDecimal("1430.02")));
		avantageRepository.findOne(1);

		av.setCode("CRO");
		avantageRepository.save(av);
		assertThat(avantageRepository.findOne(1).getCode(), equalTo("CRO"));

	}
}
