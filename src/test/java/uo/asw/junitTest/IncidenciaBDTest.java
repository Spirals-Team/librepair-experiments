package uo.asw.junitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import uo.asw.InciManagerE3aApplication;
import uo.asw.dbManagement.model.Incidencia;
import uo.asw.dbManagement.tipos.EstadoTipos;
import uo.asw.inciManager.repository.IncidenciaRepository;
import uo.asw.inciManager.util.DateUtil;

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

}
