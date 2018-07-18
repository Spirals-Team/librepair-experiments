package dev.repositories.optionSondage;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.DataJpaTestConfig;
import dev.entites.OptionSondage;
import dev.repositories.optionsondage.OptionSondageDataJpaRepository;
import dev.repositories.optionsondage.OptionSondageRepositoryDataJpa;

@ContextConfiguration(classes = { OptionSondageRepositoryDataJpa.class, DataJpaTestConfig.class })
@RunWith(SpringRunner.class)
public class OptionSondageRepositoryDataJpaTest {

	@Autowired
	private OptionSondageDataJpaRepository optionSondageRepositoryDataJpa;

	@Test
	public void testFindAll() {
		List<OptionSondage> options = optionSondageRepositoryDataJpa.findAll();
		assertThat(options.size()).isGreaterThan(0);
	}

	@Test
	public void testSave() {
		int sizeInit = optionSondageRepositoryDataJpa.findAll().size();
		OptionSondage option = new OptionSondage();
		option.setId(15L);
		option.setDescription("test");
		option.setLibelle("test desc");
		optionSondageRepositoryDataJpa.save(option);
		assertThat(sizeInit).isNotEqualTo(optionSondageRepositoryDataJpa.findAll().size());

	}

	@Test
	public void testUpdate() {
		int sizeInit = optionSondageRepositoryDataJpa.findAll().size();
		OptionSondage os = optionSondageRepositoryDataJpa.findAll().get(0);
		assertThat(os.getLibelle()).isEqualTo("libelle sondage 2");
		os.setLibelle("libelle test sondage 2");
		optionSondageRepositoryDataJpa.save(os);
		assertThat(optionSondageRepositoryDataJpa.findAll().get(0).getLibelle()).isEqualTo("libelle test sondage 2");
		assertThat(sizeInit).isEqualTo(optionSondageRepositoryDataJpa.findAll().size());
	}

	@Test
	public void testDelete() {
		int sizeInit = optionSondageRepositoryDataJpa.findAll().size();
		OptionSondage os = optionSondageRepositoryDataJpa.findAll().get(0);
		optionSondageRepositoryDataJpa.delete(os);
		assertThat(sizeInit).isGreaterThan(optionSondageRepositoryDataJpa.findAll().size());
	}
}
