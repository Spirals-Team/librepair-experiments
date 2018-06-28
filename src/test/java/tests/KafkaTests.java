package tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import inciManager.entities.*;
import inciManager.kafka.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { JSONParser.class, KafkaProducerFactory.class, SendIncidence.class,
		SendIncidenceClass.class })
public class KafkaTests {

	@Autowired
	private SendIncidence sendIncidence;

	@Test
	public void testJSON() {
		Agente agente = new Agente("id", "pass", "agente");
		Set<String> etiquetas = new HashSet<String>();
		etiquetas.add("prueba");
		Localizacion localizacion = new Localizacion(2.2, 2.5);

		Incidencia inci = new Incidencia("id", "fuego", "escripcion", etiquetas, null, Estado.ABIERTA, localizacion);
		inci.setAgenteAux(agente);

		assertEquals(
				"{\"id\":0,\"identificadorAgente\":\"id\",\"name\":\"fuego\",\"descripcion\":\"escripcion\""
						+ ",\"urlMasInfo\":null,\"etiquetas\":[\"prueba\"],\"campos\":null,\"estado\":\"ABIERTA\","
						+ "\"localizacion\":{\"latitud\":2.2,\"longitud\":2.5},\"operadorAsignado\":null}",
				JSONParser.parseIncidence(inci));

		assertEquals(
				"{\"id\":0,\"identificadorAgente\":null,\"name\":null,\"descripcion\":null,"
						+ "\"urlMasInfo\":null,\"etiquetas\":[],\"campos\":{},\"estado\":null,"
						+ "\"localizacion\":{\"latitud\":0.0,\"longitud\":0.0},\"operadorAsignado\":null}",
				JSONParser.parseIncidence(new Incidencia()));

	}

	@Test
	public void testSendIncidence() {
		Agente agente = new Agente("id", "pass", "agente");
		Set<String> etiquetas = new HashSet<String>();
		etiquetas.add("prueba");
		Localizacion localizacion = new Localizacion(2.2, 2.5);

		Incidencia inci = new Incidencia("id", "fuego", "escripcion", etiquetas, null, Estado.ABIERTA, localizacion);
		inci.setAgenteAux(agente);

		sendIncidence.sendIncidence(inci);
		
		assertNotNull(inci);
	}

}
