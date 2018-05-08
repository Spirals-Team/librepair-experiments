package uo.asw.junitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import inciManager.uo.asw.InciManagerE3aApplication;
import inciManager.uo.asw.dbManagement.model.Categoria;
import inciManager.uo.asw.dbManagement.model.Usuario;
import inciManager.uo.asw.dbManagement.tipos.CategoriaTipos;
import inciManager.uo.asw.mvc.repository.CategoriaRepository;


/**
 * Prueba la creación de una categoria, guardado y posterior borrado en la BD
 * 
 * @version abril 2018
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InciManagerE3aApplication.class)
@WebAppConfiguration
public class CategoriaTest {
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cat9, cat10, cat11;
	
	@Before
	public void setUp() {
		cat1 = new Categoria("accidente_carretera");
		cat2 = new Categoria("inundacion");
		cat3 = new Categoria("fuego");
		cat4 = new Categoria("accidente_aereo");
		cat5 = new Categoria("meteorologica");
		cat6 = new Categoria(CategoriaTipos.AMBIENTE);
		cat7 = new Categoria(CategoriaTipos.AUTOMATICO);
		cat8 = new Categoria(CategoriaTipos.CONTAMINACION);
		cat9 = new Categoria("ambiente");
		cat10 = new Categoria("automatico");
		cat11 = new Categoria("contaminacion");
		cat11 = new Categoria("no asignada");
	}

	@Test
	public void categoriaTest() {
		assertEquals(CategoriaTipos.ACCIDENTE_CARRETERA, cat1.getCategoria());
		
		cat1.setCategoria(CategoriaTipos.ACCIDENTE_AEREO);
		categoriaRepository.save(cat1);
		assertEquals(CategoriaTipos.ACCIDENTE_AEREO, cat1.getCategoria());
		
		assertEquals(CategoriaTipos.INUNDACION, cat2.getCategoria());
		assertEquals(CategoriaTipos.FUEGO, cat3.getCategoria());
		assertEquals(CategoriaTipos.ACCIDENTE_AEREO, cat4.getCategoria());
		
		String s1 = cat1.toString();
		String s2 = cat4.toString();
		assertTrue(s1.equals(s2));
		assertFalse(cat1.equals(cat2));
		
		assertEquals(CategoriaTipos.METEOROLOGICA, cat5.getCategoria());
		assertEquals(CategoriaTipos.AMBIENTE, cat6.getCategoria());
		assertEquals(CategoriaTipos.AUTOMATICO, cat7.getCategoria());
		assertEquals(CategoriaTipos.CONTAMINACION, cat8.getCategoria());
		
		categoriaRepository.delete(cat1);
	}
	
	@Test
	public void testEquals() {
		Usuario usuario = new Usuario();
		ObjectId id2 = cat1.getId();
		
		assertTrue(cat1.equals(cat1));
		assertFalse(cat1.equals(null));
		assertFalse(cat1.equals(usuario));
		
		assertFalse(cat1.equals(cat2));
		
		cat2.setCategoria(cat1.getCategoria());
		cat1.setId(null);
		assertFalse(cat1.equals(cat2));
		
		cat1.setId(id2);
		assertFalse(cat1.equals(cat2));
		
		cat2.setId(id2);
		assertTrue(cat1.equals(cat2));
	}
	
}
