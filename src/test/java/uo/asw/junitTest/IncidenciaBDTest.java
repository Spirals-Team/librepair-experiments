package uo.asw.junitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

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
import inciManager.uo.asw.dbManagement.model.Incidencia;
import inciManager.uo.asw.dbManagement.model.Propiedad;
import inciManager.uo.asw.dbManagement.model.Usuario;
import inciManager.uo.asw.dbManagement.tipos.EstadoTipos;
import inciManager.uo.asw.mvc.repository.IncidenciaRepository;
import inciManager.uo.asw.mvc.util.DateUtil;

/**
 * Prueba la creación de una incidencia, guardado y posterior borrado en la BD
 * Comprueba las propiedades de la incidencia
 * Cambios de estado y cierre
 * 
 * @version abril 2018
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InciManagerE3aApplication.class)
@WebAppConfiguration
public class IncidenciaBDTest {
	
	@Autowired
	private IncidenciaRepository incidenciaRepository;
	
	public Incidencia inci1;
	
	@Before
	public void setUp() {
		inci1 = new Incidencia("incidencia 1", "descripcion incidencia 1",
				"lat1", "long1", DateUtil.stringToDate("01/04/2018"), 
				DateUtil.stringToDate("31/12/9999"), "Id1", 
				"temperatura/20,presion/50,humedad/75", "fuego,meteorologica");
		
	}
	
	@Test
	public void incidenciaTest() {
		incidenciaRepository.save(inci1);
		assertEquals("incidencia 1", inci1.getNombreIncidencia());
		assertEquals("descripcion incidencia 1", inci1.getDescripcion());
		assertEquals("lat1", inci1.getLatitud());
		assertEquals("long1", inci1.getLongitud());
		assertEquals(DateUtil.stringToDate("01/04/2018"), inci1.getFechaEntrada());
		assertEquals(DateUtil.stringToDate("31/12/9999"), inci1.getFechaCaducidad());
		assertEquals(EstadoTipos.ABIERTA, inci1.getEstado());
		
		inci1.setEstado(EstadoTipos.EN_PROCESO);
		assertEquals(EstadoTipos.EN_PROCESO, inci1.getEstado());
		
		assertFalse(inci1.cerrarIncidencia()); //sin operario no se puede cerrar
		
		inci1.setLocation("lat01", "long01");
		assertEquals("lat01", inci1.getLatitud());
		assertEquals("long01", inci1.getLongitud());
		
		assertTrue(inci1.anularIncidencia());
		
		incidenciaRepository.delete(inci1);
	}
	
	@Test
	public void testModificacionesIncidencia() {
		inci1.setDescripcion("descripcion prueba");
		inci1.setLatitud("25");
		inci1.setLongitud("34");
		inci1.setEnterDate();
		inci1.setCaducityDate();
		
		Set<Propiedad> propiedades = new HashSet<Propiedad>();
		propiedades.add(new Propiedad("TEMPERATURA", 100.0));
		inci1.setPropiedades(propiedades);
		
		Set<Categoria> categorias = new HashSet<Categoria>();
		categorias.add(new Categoria("accidente_carretera"));
		inci1.setCategorias(categorias);
		
		assertEquals(inci1.getDescripcion(), "descripcion prueba");
		assertEquals(inci1.getLatitud()	, "25");
		assertEquals(inci1.getLongitud(), "34");
		assertEquals(propiedades, inci1.getPropiedades());
		assertEquals(categorias, inci1.getCategorias());
	}
	
	@Test
	public void testCerrarIncidencia() {
		Incidencia inci2 = new Incidencia("incidencia 1", "descripcion incidencia 1",
				"lat1", "long1", DateUtil.stringToDate("01/04/2018"), 
				DateUtil.stringToDate("31/12/9999"), "Id1", 
				"temperatura/20,presion/50,humedad/75", "fuego,meteorologica");
		
		assertFalse(inci2.cerrarIncidencia());
		
		inci2.setOperario(new Usuario());
		assertFalse(inci2.cerrarIncidencia());
		
		inci2.setEstado(EstadoTipos.EN_PROCESO);
		assertTrue(inci2.cerrarIncidencia());
	}
	
	@Test
	public void testAnularIncidencia() {
		Incidencia inci2 = new Incidencia("incidencia 1", "descripcion incidencia 1",
				"lat1", "long1", DateUtil.stringToDate("01/04/2018"), 
				DateUtil.stringToDate("31/12/9999"), "Id1", 
				"temperatura/20,presion/50,humedad/75", "fuego,meteorologica");
		
		assertTrue(inci2.anularIncidencia());
		
		inci2.setEstado(EstadoTipos.CERRADA);
		assertFalse(inci2.anularIncidencia());
	}
	
	@Test
	public void testSetImageURL() {
		inci1.setImageURL("ruta");
		assertEquals(inci1.getImageURL(), "ruta");
	}
	
	@Test
	public void testEquals() {
		Usuario usuario = new Usuario();
		Incidencia inci2 = new Incidencia("incidencia 2", "descripcion incidencia 2",
				"lat1", "long1", DateUtil.stringToDate("01/04/2018"), 
				DateUtil.stringToDate("31/12/9999"), "Id1", 
				"temperatura/20,presion/50,humedad/75", "fuego,meteorologica");
		ObjectId id1= inci1.getId();
		
		assertTrue(inci1.equals(inci1));
		assertFalse(inci1.equals(null));
		assertFalse(inci1.equals(usuario));
		
		inci1.setFechaEntrada(null);
		assertFalse(inci1.equals(inci2));
		
		inci1.setFechaEntrada(DateUtil.stringToDate("02/04/2018"));
		assertFalse(inci1.equals(inci2));
		

		inci2.setFechaEntrada(inci1.getFechaEntrada());
		inci1.setId(null);
		assertFalse(inci1.equals(inci2));
		
		inci1.setId(id1);
		assertFalse(inci1.equals(inci2));
		
		inci2.setId(inci1.getId());
		inci1.setNombreIncidencia(null);
		assertFalse(inci1.equals(inci2));
		
		inci1.setNombreIncidencia("incidencia 1");
		assertFalse(inci1.equals(inci2));
		
		inci2.setNombreIncidencia(inci1.getNombreIncidencia());
		assertTrue(inci1.equals(inci2));
	}

}
