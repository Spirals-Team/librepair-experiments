package dominio;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uo.asw.entities.Agent;
import uo.asw.entities.Incidence;
import uo.asw.entities.TipoIncidencia;

public class IncidenciaTest 
{
	private Incidence inci1, inci2, inci3;
	private Agent a1=new Agent();
	
	@Before
	public void setUp()
	{
		List<String> tags=new ArrayList<String>();
		tags.add("terremoto");
		tags.add("seismo");
		inci1=new Incidence("Incidencia1", "Terremoto", a1.getEmail(), tags , TipoIncidencia.SENSOR_SEISMO, 40.2);
		tags.remove("gas");
		tags.remove("fugas");
		tags.add("inundacion");
		tags.add("agua");
		inci2=new Incidence("Incidencia2", "Inundación del sotano", a1.getEmail(), tags, TipoIncidencia.SENSOR_INUNDACION, 100.2);
		tags.remove("agua");
		tags.remove("inundacion");
		tags.add("caldera");
		tags.add("calor");
		inci3=new Incidence("Incidencia3", "Sobrecalentamiento de caldera", a1.getEmail(), tags, TipoIncidencia.SENSOR_TEMPERATURA, 1000.1);
	}
	
	@Test
	public void testNombre() 
	{
		assertTrue(inci1.getName().equals("Incidencia1"));
		assertTrue(inci2.getName().equals("Incidencia2"));
		assertTrue(inci3.getName().equals("Incidencia3"));
		inci1.setName("Inci1");
		inci2.setName("Inci2");
		inci3.setName("Inci3");
		assertTrue(inci1.getName().equals("Inci1"));
		assertTrue(inci2.getName().equals("Inci2"));
		assertTrue(inci3.getName().equals("Inci3"));
	}
	
	@Test
	public void testTipo()
	{
		System.out.println(inci3.getTipo());
		assertTrue(inci1.getTipo().equals(TipoIncidencia.SENSOR_SEISMO));
		assertTrue(inci2.getTipo().equals(TipoIncidencia.SENSOR_INUNDACION));
		assertTrue(inci3.getTipo().equals(TipoIncidencia.SENSOR_TEMPERATURA));
		inci1.setTipo(inci2.getTipo());
		inci2.setTipo(inci3.getTipo());
		inci3.setTipo(inci1.getTipo());
		assertTrue(inci1.getTipo().equals(TipoIncidencia.SENSOR_INUNDACION));
		assertTrue(inci2.getTipo().equals(TipoIncidencia.SENSOR_TEMPERATURA));
		assertTrue(inci3.getTipo().equals(TipoIncidencia.SENSOR_INUNDACION));
	}
	
}
