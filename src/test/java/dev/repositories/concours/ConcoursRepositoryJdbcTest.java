package dev.repositories.concours;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.DataSourceTestConfig;
import dev.entites.Concours;

@ContextConfiguration(classes = { ConcoursRepositoryJdbc.class, DataSourceTestConfig.class })
@RunWith(SpringRunner.class)

public class ConcoursRepositoryJdbcTest {

	@Autowired
	private ConcoursRepositoryJdbc concoursRepositoryJdbc;

	@Test
	public void testFindAll() {
		List<Concours> c = concoursRepositoryJdbc.findAll();
		assertThat(c.size()).isEqualTo(2);
	}

}
