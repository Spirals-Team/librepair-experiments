package dev.repositories.optionSondage;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.DataSourceTestConfig;
import dev.entites.OptionSondage;
import dev.repositories.optionsondage.OptionSondageRepositoryJdbc;

@ContextConfiguration(classes = { OptionSondageRepositoryJdbc.class, DataSourceTestConfig.class })
@RunWith(SpringRunner.class)
public class OptionSondageRepositoryJdbcTest {

	@Autowired
	private OptionSondageRepositoryJdbc optionSondageRepositoryJdbc;

	@Test
	public void findAll_Test() {
		int resultCount = optionSondageRepositoryJdbc.findAll().size();
		assertThat(resultCount).isEqualTo(7);
	}

	@Test
	public void save_Test() {
		int resultCount = optionSondageRepositoryJdbc.findAll().size();
		assertThat(resultCount).isEqualTo(6);

		OptionSondage os = new OptionSondage();
		os.setLibelle("save_test()");
		os.setDescription("fonction test save dans JUNIT");
		optionSondageRepositoryJdbc.save(os);

		resultCount = optionSondageRepositoryJdbc.findAll().size();
		assertThat(resultCount).isEqualTo(7);
	}

	@Test
	public void update_Test() {
		OptionSondage os = optionSondageRepositoryJdbc.findAll().get(2);
		assertThat(os.getLibelle()).isEqualTo("libelle sondage 4");

		os.setLibelle("Libelle sondage 4 test");
		optionSondageRepositoryJdbc.update(os);

		os = optionSondageRepositoryJdbc.findAll().get(2);
		assertThat(os.getLibelle()).isEqualTo("Libelle sondage 4 test");
	}

	@Test
	public void delete_Test() {
		int resultCount = optionSondageRepositoryJdbc.findAll().size();
		assertThat(resultCount).isEqualTo(7);

		OptionSondage os = optionSondageRepositoryJdbc.findAll().get(2);
		optionSondageRepositoryJdbc.delete(os);

		resultCount = optionSondageRepositoryJdbc.findAll().size();
		assertThat(resultCount).isEqualTo(6);
	}
}
