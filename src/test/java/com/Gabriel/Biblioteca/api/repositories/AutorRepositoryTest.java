package com.Gabriel.Biblioteca.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.Gabriel.Biblioteca.api.entities.Autor;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("ambienteTest")
public class AutorRepositoryTest {
	
	private static final String CODIGO = "55555";
	
	@Autowired
	private AutorRepository autorRepository;

	@Before
	public void setUp() throws Exception {
		Autor autor = new Autor();
		autor.setCodigo(CODIGO);
		autor.setNome("Gabriel");
		autor.setSobrenome("Schmitz");
		this.autorRepository.save(autor);
	}
	
	@After
	public final void tearDown() {
		this.autorRepository.deleteAll();
	}

	@Test
	public void testeBuscaPorCodigo() {
		Autor autor = this.autorRepository.procuraPorCodigo("55555");

		assertEquals(CODIGO, autor.getCodigo());
	}
}
