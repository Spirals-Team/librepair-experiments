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
import inciManager.uo.asw.dbManagement.model.Propiedad;
import inciManager.uo.asw.dbManagement.model.Usuario;
import inciManager.uo.asw.dbManagement.tipos.PropiedadTipos;
import inciManager.uo.asw.mvc.repository.PropiedadRepository;

/**
 * Prueba la creación de una propiedad, guardado y posterior borrado en la BD
 * 
 * @version abril 2018
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InciManagerE3aApplication.class)
@WebAppConfiguration
public class PropiedadTest {
	@Autowired
	private PropiedadRepository propiedadRepository;
	
	public Propiedad pro1, pro2, pro3, pro4, pro5, pro6, pro7, pro8;
	
	@Before
	public void setUp() {
		pro1 = new Propiedad("temperatura", 20.0);
		pro2 = new Propiedad("presion", 50.0);
		pro3 = new Propiedad("humedad", 70.0);
		pro4 = new Propiedad(PropiedadTipos.VELOCIDAD_VIENTO, 100.0);
		pro5 = new Propiedad(PropiedadTipos.VELOCIDAD_CIRCULACION, 120.0);
		pro6 = new Propiedad("nivel_polucion", 200.0);
		pro7 = new Propiedad("calidad_aire", 70.0);
		pro8 = new Propiedad("no asignado", 70.0);
	}

	@Test
	public void test() {
		propiedadRepository.save(pro1);
		assertEquals(PropiedadTipos.TEMPERATURA, pro1.getPropiedad());
		assertEquals(20.0, pro1.getValor(), 0.01);
		
		assertEquals(PropiedadTipos.PRESION, pro2.getPropiedad());
		assertEquals(50.0, pro2.getValor(), 0.01);
		
		pro2.setPropiedad(PropiedadTipos.TEMPERATURA);
		pro2.setValor(20.0);
		
		assertTrue(pro1.toString().equals(pro2.toString()));
		
		assertEquals(PropiedadTipos.HUMEDAD, pro3.getPropiedad());
		assertEquals(70.0, pro3.getValor(), 0.01);
		
		assertEquals(PropiedadTipos.VELOCIDAD_VIENTO, pro4.getPropiedad());
		assertEquals(100.0, pro4.getValor(), 0.01);
		
		assertEquals(PropiedadTipos.VELOCIDAD_CIRCULACION, pro5.getPropiedad());
		assertEquals(120.0, pro5.getValor(), 0.01);
		
		assertEquals(PropiedadTipos.NIVEL_POLUCION, pro6.getPropiedad());
		assertEquals(200.0, pro6.getValor(), 0.01);
		
		assertEquals(PropiedadTipos.CALIDAD_AIRE, pro7.getPropiedad());
		assertEquals(70.0, pro7.getValor(), 0.01);
		
		pro3.setMaximo(15.0);
		assertEquals(15.0, pro3.getMaximo(), 0.1);
		pro3.setMinimo(12.0);
		assertEquals(12.0, pro3.getMinimo(), 0.1);
		
		propiedadRepository.delete(pro1);
	}
	
	@Test
	public void testEquals() {
		Usuario usuario = new Usuario();
		ObjectId id1 = pro1.getId();
		
		assertTrue(pro1.equals(pro1));
		assertFalse(pro1.equals(null));
		assertFalse(pro1.equals(usuario));
		
		pro1.setId(null);
		assertFalse(pro1.equals(pro2));
		
		pro1.setId(id1);
		assertFalse(pro1.equals(pro2));
		
		pro2.setId(id1);
		assertFalse(pro1.equals(pro2));
		
		pro2.setPropiedad(pro1.getPropiedad());
		pro1.setValor(null);
		assertFalse(pro1.equals(pro2));
		
		pro1.setValor(3.0);
		assertFalse(pro1.equals(pro2));
		
		pro2.setValor(pro1.getValor());
		assertTrue(pro1.equals(pro2));
	}
}
