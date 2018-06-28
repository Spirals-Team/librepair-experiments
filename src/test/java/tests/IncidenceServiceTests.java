package tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import inciManager.StarterApplication;
import inciManager.entities.Agente;
import inciManager.entities.Estado;
import inciManager.entities.Incidencia;
import inciManager.entities.Localizacion;
import inciManager.incidenceService.IncidenceServiceFacade;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StarterApplication.class })
public class IncidenceServiceTests {

	@Autowired
	private IncidenceServiceFacade inciService;

	@Test
	public void testInciService() {
		Agente agente = new Agente("id", "pass", "agente");
		Set<String> etiquetas = new HashSet<String>();
		etiquetas.add("prueba");
		Localizacion localizacion = new Localizacion(2.2, 2.5);

		Incidencia inci = new Incidencia(null, "fuego", "escripcion", etiquetas, null, null, localizacion);
		inci.setAgenteAux(agente);

		Incidencia processed = inciService.processIncidence(inci);

		assertNull(inciService.processIncidence(null));
		assertTrue(processed.getEstado().equals(Estado.ABIERTA));
		assertTrue(processed.getIdentificadorAgente().equals(agente.getIdentificador()));
		assertTrue(processed.getId() != 0);

		assertTrue(inciService.getIncidenceInfo(agente).contains(processed));
		
		assertNull(inciService.getIncidenceInfo(null));
		
		inci.getAgenteAux().setKind("sensor");
		inci.setId(0);
		
		assertTrue(inciService.processIncidence(inci).getId() == 0); //No se guarda en la BBDD
		
	}

}
