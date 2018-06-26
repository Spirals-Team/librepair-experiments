package bbdd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uo.asw.ManagerApplication;
import uo.asw.entities.Agent;
import uo.asw.entities.Incidence;
import uo.asw.entities.TipoIncidencia;
import uo.asw.repositories.IncidenceRepository;
import uo.asw.services.IncidenceService;

@SpringBootTest(classes = { ManagerApplication.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseDatosTest 
{
	
	@Autowired
	private IncidenceRepository inciRepository;
	
	@Autowired
	private IncidenceService inciService;
	
	private Incidence inci;
	private Agent agente;
	

	@Before
	public void setUp() 
	{
		agente=new Agent("Miguel", "uniovi2", "m@uniovi.es", "M2", "Pravia", "Persona", 2);
		inci=new Incidence("Fuga de agua", "Fuga de agua en una alcantarilla cerca del ayuntamiento ", agente.getEmail(), null, TipoIncidencia.SENSOR_INUNDACION, 200.2);
		inciService.saveIncidence(inci);
	}
	
	@After
	public void tearDown()
	{
		inciRepository.delete(inci);
	}
	
	@Test
	public void testExistencia()
	{
		assertEquals(inci.getId(), inciService.getIncidenceById(inci.getId()).getId());
		assertEquals(1, inciRepository.findByEmailAgente("m@uniovi.es").size());
	}
	
	@Test
	public void testCopiaIncidencia()
	{
		Incidence copia=inciService.getIncidenceById(inci.getId());
		assertTrue(copia.getTipo().equals(TipoIncidencia.SENSOR_INUNDACION));
		copia.setTipo(TipoIncidencia.SENSOR_SEISMO);
		inciRepository.save(copia);
		assertTrue(copia.getTipo().equals(TipoIncidencia.SENSOR_SEISMO));
		inciRepository.delete(copia);
	}
	
	@Test
	public void nuevaIncidenciaEnNuevoAgente()
	{
		//agente=new Agent("Manolo", "Persona", 2);
		//agente.setIdentificador("M3");
		//Incidence inci2=new Incidence();
		//inci2.setName("Problema eléctrico");
		//inci2.setAgente(agente);
		//inciRepository.save(inci2);
		//assertEquals(2, inciRepository.findByAgentIdentificador("M2").size()+inciRepository.findByAgentIdentificador("M3").size());
		//inciRepository.delete(inci2);
	}

}
