package uo.asw.junitTest;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import uo.asw.InciManagerE3aApplication;
import uo.asw.dbManagement.model.Usuario;
import uo.asw.dbManagement.model.ValorLimite;
import uo.asw.dbManagement.tipos.PerfilTipos;

/**
 * Prueba la clase ValorLimite de los umbrales permitidos en las
 * propiedades de las incidencias
 * 
 * @version abril 2018
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InciManagerE3aApplication.class)
@WebAppConfiguration
public class ValorLimiteTest {
	private ValorLimite vlTemp;
	private ValorLimite vl2, vl3;
	
	@Before
	public void setUp() {
		vlTemp = new ValorLimite(100, 0, "temperatura", true,
				true);
		vl2 = new ValorLimite("presion", 100, 40);
		vl3 = new ValorLimite("temperatura", 200, 30);
		
	}

	@Test
	public void valorLimiteTest() {
		assertEquals("temperatura", vlTemp.getPropiedad());
		assertEquals(100.0, vlTemp.getValorMax(), 0.01);
		assertEquals(0.0, vlTemp.getValorMin(), 0.01);
		assertTrue(vlTemp.isMaxCritico());
		assertTrue(vlTemp.isMinCritico());
		
		vl2.setPropiedad("humedad");
		assertEquals("humedad", vl2.getPropiedad());
		vl2.setValorMax(100);
		assertEquals(100.0, vl2.getValorMax(), 0.01);
		vl2.setValorMin(0);
		assertEquals(0.0, vl2.getValorMin(), 0.01);
		vl2.setMinCritico(true);
		vl2.setMaxCritico(false);
		assertFalse(vl2.isMaxCritico());
		assertTrue(vl2.isMinCritico());
		
		assertFalse(vlTemp.equals(vl2));
		
	}
	
	@Test
	public void testEquals() {
		Usuario usuario = new Usuario();
		ObjectId id2 = vl2.getId();
		
		assertTrue(vl2.equals(vl2));
		assertFalse(vl2.equals(null));
		assertFalse(vl2.equals(usuario));
		
		vl2.setId(null);
		assertFalse(vl2.equals(vl3));
		
		vl2.setId(id2);
		assertFalse(vl2.equals(vl3));
		
		vl3.setId(id2);
		assertFalse(vl2.equals(vl3));
		
		vl3.setPropiedad(vl2.getPropiedad());
		assertFalse(vl2.equals(vl3));
		
		vl3.setValorMax(vl2.getValorMax());
		assertFalse(vl2.equals(vl3));
		
		vl3.setValorMin(vl2.getValorMin());
		assertTrue(vl2.equals(vl3));
	}

}
