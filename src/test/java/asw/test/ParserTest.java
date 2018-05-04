package asw.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import asw.Application;
import asw.services.InsercionDatosService;
import asw.streamKafka.consumidor.KafkaConsumer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class ParserTest 
{
	@Autowired
	private KafkaConsumer consumidorKafka;
	
	@Autowired
	private InsercionDatosService insDatos;
	
	@Test
	public void parseadorCampos() {
		insDatos.init();
		
		String incidencia = "Juan@Fuego en Oviedo@El parque San Francisco esta quemandose a causa de un cigarrillo mal apagado@"
							+ "43.3616142$-5.8506767@Fuego$Parque@Temperatura:Alta$Fuego:Extremo@1521893518784";
		
		String inci_json = consumidorKafka.parseToIncidence(incidencia);
		
		Assert.assertTrue( inci_json.contains("\"nombre_incidencia\" : \"Fuego en Oviedo\"") );
		Assert.assertTrue( inci_json.contains("\"estado\" : \"ABIERTO\"") );
	}
}
