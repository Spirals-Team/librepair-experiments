package dev.repositories.examen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.DataSourceTestConfig;
import dev.repositories.RepositoryTestConfig;

@ContextConfiguration(classes = { ExamenRepositoryJdbc.class, DataSourceTestConfig.class, RepositoryTestConfig.class })
@RunWith(SpringRunner.class)
public class ExamenRepositoryJdbcTest {

	@Autowired
	private ExamenRepositoryJdbc examenRepositoryJdbc;

	@Test
	public void testConnexion() {

	}

}
