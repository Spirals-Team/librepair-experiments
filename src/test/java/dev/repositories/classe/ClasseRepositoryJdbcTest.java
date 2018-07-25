package dev.repositories.classe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.DataSourceTestConfig;
import dev.entites.Classe;

@ContextConfiguration(classes = { ClasseRepositoryJdbc.class, DataSourceTestConfig.class })
@RunWith(SpringRunner.class)
public class ClasseRepositoryJdbcTest {

	@Autowired
	private ClasseRepositoryJdbc classeRepositoryJdbc;

	@Test
	public void testFindAll() {
		List<Classe> classes = classeRepositoryJdbc.findAll();
		assertThat(classes).isNotEmpty();
	}

	@Test
	public void testSave() {
		Classe classe = new Classe("D12");
		List<Classe> classes = classeRepositoryJdbc.findAll();
		int size = classes.size();
		classeRepositoryJdbc.save(classe);
		classes = classeRepositoryJdbc.findAll();
		assertThat(classes.size()).isEqualTo(size + 1);
		assertThat(classes.get(size).getNom()).isEqualTo("D12");
	}

	@Test
	public void TestUpdate() {
		List<Classe> classes = classeRepositoryJdbc.findAll();
		Classe classe = classes.get(0);
		classe.setNom("D15");
		classeRepositoryJdbc.update(classe);
		assertThat(classes.get(0).getNom()).isEqualTo("D15");
	}

	@Test
	public void testDelete() {
		List<Classe> classes = classeRepositoryJdbc.findAll();
		int size = classes.size();

		Classe c1 = new Classe();
		for (Classe c : classes) {
			if (c.getNom().equals("d12-sans-stagiaires")) {
				c1 = c;
			}
		}

		Long id = c1.getId();

		classeRepositoryJdbc.delete(c1);

		classes = classeRepositoryJdbc.findAll();

		assertThat(classes.get(0).getId()).isNotEqualTo(id);
	}

	@Test
	public void testFindById() {
		Long id = 1L;
		Optional<Classe> classe = classeRepositoryJdbc.findById(id);

		if (classe.isPresent()) {
			Classe classe2 = classe.get();

			assertThat(classe2).isNotNull();
			assertThat(classe2.getId()).isEqualTo(id);

		} else {
			fail();
		}

	}

}
