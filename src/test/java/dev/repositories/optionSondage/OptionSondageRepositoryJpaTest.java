package dev.repositories.optionSondage;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.JpaTestConfig;
import dev.entites.OptionSondage;
import dev.repositories.optionsondage.OptionSondageRepository;
import dev.repositories.optionsondage.OptionSondageRepositoryJpa;

@ContextConfiguration(classes = { OptionSondageRepositoryJpa.class, JpaTestConfig.class })
@RunWith(SpringRunner.class)
public class OptionSondageRepositoryJpaTest {

	@Autowired
	private OptionSondageRepository optionSondageRepositoryJpa;

	@Test
	public void findAll() {
		assertThat(optionSondageRepositoryJpa.findAll().size()).isEqualTo(6);
	}

	@Test
	public void save_test() {
		int resultCount = optionSondageRepositoryJpa.findAll().size();
		assertThat(resultCount).isEqualTo(6);

		OptionSondage os = new OptionSondage();
		os.setLibelle("save_test()");
		os.setDescription("fonction test save dans JUNIT");
		optionSondageRepositoryJpa.save(os);

		resultCount = optionSondageRepositoryJpa.findAll().size();
		assertThat(resultCount).isEqualTo(7);
	}

	@Test
	public void update_Test() {
		OptionSondage os = optionSondageRepositoryJpa.findAll().get(2);
		assertThat(os.getLibelle()).isEqualTo("libelle sondage 4");

		os.setLibelle("Libelle sondage 4 test");
		optionSondageRepositoryJpa.update(os);

		os = optionSondageRepositoryJpa.findAll().get(2);
		assertThat(os.getLibelle()).isEqualTo("Libelle sondage 4 test");
	}

	@Test
	public void delete_Test() {
		int resultCount = optionSondageRepositoryJpa.findAll().size();
		assertThat(resultCount).isEqualTo(7);

		OptionSondage os = optionSondageRepositoryJpa.findAll().get(2);
		optionSondageRepositoryJpa.delete(os);

		resultCount = optionSondageRepositoryJpa.findAll().size();
		assertThat(resultCount).isEqualTo(6);
	}

}
