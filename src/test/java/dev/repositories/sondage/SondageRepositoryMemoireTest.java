package dev.repositories.sondage;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.entites.Sondage;
import dev.repositories.sondage.SondageRepositoryMemoire;

@ContextConfiguration(classes = { SondageRepositoryMemoire.class })
@RunWith(SpringRunner.class)
public class SondageRepositoryMemoireTest {

	@Autowired
	private SondageRepositoryMemoire service;

	@Test
	public void testFindAll() {
		List<Sondage> s = service.findAll();
		assertThat(s.size()).isEqualTo(3);
	}

	@Test
	public void testSave() {
		assertThat(service.findAll().size()).isEqualTo(3);
		service.save(new Sondage());
		assertThat(service.findAll().size()).isEqualTo(4);
	}

	@Test
	public void testUpdate() {
		assertThat(service.findAll().get(0).getTitre()).isEqualTo("Titre sondage 2");
		Sondage s = service.findAll().get(0);
		s.setTitre("JUNIT TEST");
		service.update(s);
		assertThat(service.findAll().get(0).getTitre()).isEqualTo("JUNIT TEST");
	}

	@Test
	public void testDelete() {
		assertThat(service.findAll().size()).isEqualTo(4);
		Sondage s = new Sondage();
		s.setId(1L);
		service.delete(s);
		assertThat(service.findAll().size()).isEqualTo(3);
	}

}
