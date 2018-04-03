package uo.asw.junitTest;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import uo.asw.InciManagerE3aApplication;
import uo.asw.dbManagement.model.Incidencia;
import uo.asw.dbManagement.model.Usuario;
import uo.asw.dbManagement.tipos.PerfilTipos;
import uo.asw.inciManager.repository.IncidenciaRepository;
import uo.asw.inciManager.repository.UsuarioRepository;
import uo.asw.inciManager.util.DateUtil;


/**
 * Prueba la creación de un usuario, guardado y posterior borrado en la BD
 * Comprueba las propiedades y las incidencias del usuario
 * 
 * @version abril 2018
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InciManagerE3aApplication.class)
@WebAppConfiguration
public class UsuarioBDTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private IncidenciaRepository incidenciaRepository;
	
	private Usuario user1;
	private Incidencia inci1, inci2, inci3;

	@Before
	public void setUp() {
		user1 = new Usuario("nombre1", "apellidos1", "usuario1@dominio.es",
				"Id001", "123456", PerfilTipos.OPERARIO);
		
		inci1 = new Incidencia("incidencia 1", "descripcion incidencia 1",
				"lat1", "long1", DateUtil.stringToDate("01/04/2018"), 
				DateUtil.stringToDate("31/12/9999"), "Id001", 
				"temperatura/20,presion/50,humedad/75", "fuego,meteorologica");
		inci2 = new Incidencia("incidencia 2", "descripcion incidencia 2",
				"lat2", "long2", DateUtil.stringToDate("01/04/2018"), 
				DateUtil.stringToDate("31/12/9999"), "Id001", 
				"temperatura/20,presion/50,humedad/75", "fuego,meteorologica");
		inci3 = new Incidencia("incidencia 3", "descripcion incidencia 3",
				"lat3", "long3", DateUtil.stringToDate("01/04/2018"), 
				DateUtil.stringToDate("31/12/9999"), "Id001", 
				"temperatura/20,presion/50,humedad/75", "fuego,meteorologica");
	}
	
	@Test
	public void usuarioTest() {
		usuarioRepository.save(user1);
		assertEquals("nombre1", user1.getNombre());
		assertEquals("apellidos1", user1.getApellidos());
		assertEquals("usuario1@dominio.es", user1.getEmail());
		assertEquals("Id001", user1.getIdentificador());
		assertEquals(PerfilTipos.OPERARIO, user1.getPerfil());
		
		assertEquals (0, user1.getIncidencias().size());
		incidenciaRepository.save(inci1);
		incidenciaRepository.save(inci2);
		incidenciaRepository.save(inci3);
		Set<Incidencia> incidencias = new HashSet<Incidencia>();
		incidencias.add(inci1);
		incidencias.add(inci2);
		incidencias.add(inci3);
		user1.setIncidencias(incidencias);
		
		assertEquals(3, user1.getIncidencias().size());
		
		incidenciaRepository.delete(inci1);
		incidenciaRepository.delete(inci2);
		incidenciaRepository.delete(inci3);
		
		usuarioRepository.delete(user1);
	}

}
