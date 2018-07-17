package dev.repositories.sondage;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.DataJpaTestConfig;
import dev.entites.Sondage;

@ContextConfiguration(classes = { SondageRepositoryDataJpa.class, DataJpaTestConfig.class })
@RunWith(SpringRunner.class)
public class SondageRepositoryDataJpaTest {

	@Autowired
	private SondageDataJpaRepository sondageRepositoryDataJpa;

	@Test
	public void testFindAll() {
		List<Sondage> options = sondageRepositoryDataJpa.findAll();
		for (Sondage s : options) {
			System.out.println(s.getTitre());
		}
		assertThat(options.size()).isGreaterThan(0);
	}

	@Test
	public void testSave() {
		int sizeInit = sondageRepositoryDataJpa.findAll().size();
		Sondage sondage = new Sondage();
		sondage.setId(15L);
		sondage.setTitre("Classe Test");
		sondageRepositoryDataJpa.save(sondage);
		assertThat(sizeInit).isNotEqualTo(sondageRepositoryDataJpa.findAll().size());

	}

	@Test
	public void testUpdate() {
		int sizeInit = sondageRepositoryDataJpa.findAll().size();
		Sondage os = sondageRepositoryDataJpa.findAll().get(0);
		assertThat(os.getTitre()).isEqualTo("titre sondage 1");
		os.setTitre("Titre test sondage 1");
		sondageRepositoryDataJpa.save(os);
		assertThat(sondageRepositoryDataJpa.findAll().get(0).getTitre()).isEqualTo("Titre test sondage 1");
		assertThat(sizeInit).isEqualTo(sondageRepositoryDataJpa.findAll().size());
	}

	@Test
	public void testDelete() {
		int sizeInit = sondageRepositoryDataJpa.findAll().size();
		Sondage os = sondageRepositoryDataJpa.findAll().get(1);
		sondageRepositoryDataJpa.delete(os);
		assertThat(sizeInit).isGreaterThan(sondageRepositoryDataJpa.findAll().size());
	}

	@Test
	public void testFindById() {
		Sondage options;
		try {
			options = sondageRepositoryDataJpa.findById(1L).orElseThrow(Exception::new);
			assertThat(options.getTitre()).isEqualTo("titre sondage 1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
