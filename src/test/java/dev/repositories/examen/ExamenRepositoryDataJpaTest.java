package dev.repositories.examen;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.config.DataJpaTestConfig;

@ContextConfiguration(classes = { ExamenRepositoryDataJpa.class, DataJpaTestConfig.class })
@RunWith(SpringRunner.class)
public class ExamenRepositoryDataJpaTest {

	@Autowired
	private ExamenRepository entiteRepositoryDataJpa;

	@Test
	public void testSelect() {
		assertTrue(entiteRepositoryDataJpa.findAll().size() == 2);

	}
}
