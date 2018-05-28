package repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.ServicesConfig;
import dev.paie.entite.Avantage;
import dev.paie.repository.AvantageRepository;

@ContextConfiguration(classes = { ServicesConfig.class })
@RunWith(SpringRunner.class)
public class AvantageRepositoryTest {

	@Autowired
	private AvantageRepository avantageRepository;

	private Avantage avantage;

	private Avantage avantage1;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		avantage = new Avantage(new Integer(1), "A01", "first", new BigDecimal("1000"));

		avantageRepository.deleteAll();

		avantageRepository.save(avantage);

		assertThat(avantageRepository.findByCode(avantage.getCode()).contains(avantage));

		avantage.setCode("A00");

		avantageRepository.save(avantage);

		assertThat(avantageRepository.findAll().contains(avantage));
	}
}
