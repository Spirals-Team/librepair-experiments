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
import inciManager.inciRepository.*;;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StarterApplication.class)
public class InciRepositoryTest {

	@Autowired
	private InciRepositoryFacade inciRepository;

	@Test
	public void testRepository() {
		Agente agente = new Agente("agentePrueba", "pass", "agente");
		Set<String> etiquetas = new HashSet<String>();
		etiquetas.add("prueba");
		Localizacion localizacion = new Localizacion(2.2, 2.5);

		Incidencia inci = new Incidencia("id", "fuego", "escripcion", etiquetas, null, Estado.ABIERTA, localizacion);
		inci.setAgenteAux(agente);

		String id = inciRepository.saveIncidence(inci).getIdentificadorAgente();

		assertTrue(inciRepository.getIncidence(id).contains(inci));
		assertNotNull(inciRepository.getOperators());

	}

}
