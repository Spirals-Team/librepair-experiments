package tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import inciManager.entities.Agente;
import inciManager.entities.Estado;
import inciManager.entities.Incidencia;
import inciManager.entities.Localizacion;
import inciManager.incidenceController.RestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { RestUtil.class })
public class IncidenceControllerTests {

	@Test
	public void test() {
		Agente agente = new Agente("id", "pass", "agente");
		Set<String> etiquetas = new HashSet<String>();
		etiquetas.add("prueba");
		Localizacion localizacion = new Localizacion(2.2, 2.5);

		Incidencia inci = new Incidencia("id", "fuego", "escripcion", etiquetas, null, Estado.ABIERTA, localizacion);
		inci.setAgenteAux(agente);
		
		
		assertNotNull(inci);
		// Se probarán con selenium y cucumber
	}

}
