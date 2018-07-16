package dev.repositories.optionSondage;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.entites.OptionSondage;
import dev.repositories.optionsondage.OptionSondageRepositoryMemoire;

@ContextConfiguration(classes = { OptionSondageRepositoryMemoire.class })
@RunWith(SpringRunner.class)
public class OptionSondageRepositoryMemoireTest {

	@Autowired
	private OptionSondageRepositoryMemoire service;

	@Test
	public void testFindAll() {
		List<OptionSondage> s = service.findAll();
		assertThat(s.size()).isEqualTo(3);
	}

	@Test
	public void testSave() {
		assertThat(service.findAll().size()).isEqualTo(3);
		service.save(new OptionSondage());
		assertThat(service.findAll().size()).isEqualTo(4);
	}

	@Test
	public void testUpdate() {
		assertThat(service.findAll().get(0).getLibelle()).isEqualTo("option 2");
		OptionSondage s = service.findAll().get(0);
		s.setLibelle("JUNIT TEST");
		service.update(s);
		assertThat(service.findAll().get(0).getLibelle()).isEqualTo("JUNIT TEST");
	}

	@Test
	public void testDelete() {
		assertThat(service.findAll().size()).isEqualTo(4);
		OptionSondage s = new OptionSondage();
		s.setId(1L);
		service.delete(s);
		assertThat(service.findAll().size()).isEqualTo(3);
	}

}
