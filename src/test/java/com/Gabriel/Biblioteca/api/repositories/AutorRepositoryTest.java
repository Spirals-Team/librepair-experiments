package com.Gabriel.Biblioteca.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.Gabriel.Biblioteca.api.entities.Autor;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AutorRepositoryTest {

	@Autowired
	private AutorRepository autorRepository;
	
	
	private static final String CODIGO = "55555";


	@Before
	public void setUp() throws Exception {
		Autor autor = new Autor();
		autor.setCodigo(CODIGO);
		autor.setNome("Gabriel");
		autor.setSobrenome("Schmitz");
		this.autorRepository.save(autor);
	}

	@After
	public void tearDown() {
		this.autorRepository.deleteAll();
	}
	
	@Test
	public void testFindByCodigo() {
		Autor autor = this.autorRepository.findByCodigo(CODIGO);
		assertEquals(CODIGO, autor.getCodigo());
	}
	
	
	
	
}
