package dev.paie.repository;

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
import dev.paie.entite.Avantage;

@ContextConfiguration(classes = { ServicesConfig.class })

@RunWith(SpringRunner.class)
public class AvantageRepositoryTest {
	@Autowired
	private AvantageRepository avantageRepository;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		// TODO sauvegarder un nouvel avantage
		Avantage nouvelAvantage = new Avantage();
		nouvelAvantage.setCode("fillon");
		nouvelAvantage.setNom("Penelope");
		nouvelAvantage.setMontant(new BigDecimal("100000.00"));

		avantageRepository.save(nouvelAvantage);

		// TODO vérifier qu'il est possible de récupérer le nouvel avantage via
		// la méthode findOne
		Avantage avantageTrouve = avantageRepository.findOne(10);
		assertThat(avantageTrouve.getCode(), equalTo("fillon"));

		// TODO modifier un avantage
		avantageTrouve.setCode("Kadhafi");
		avantageTrouve.setMontant(new BigDecimal("20000000.00"));
		avantageRepository.save(avantageTrouve);
		// TODO vérifier que les modifications sont bien prises en compte via la
		// méthode findOne
		Avantage avantageTrouve2 = avantageRepository.findOne(10);
		assertThat(avantageTrouve2.getCode(), equalTo("Kadhafi"));

		// TODO vérifier la méthode findByCode(String code)
		List<Avantage> listKadhafi = avantageRepository.findByCode("Kadhafi");
		assertThat(listKadhafi.get(0).getId(), equalTo(10));
	}

}
