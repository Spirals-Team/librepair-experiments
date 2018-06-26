package dominio;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import uo.asw.entities.Agent;

public class AgenteTest 
{

	private Agent agente1, agente2, agente3;
	
	@Before
	public void setUp()
	{
		agente1=new Agent("Agente1", "agente1", "agente1@uniovi.es", "A1", "Oviedo", "Persona", 2);
		agente2=new Agent("Agente2", "agente2", "agente2@uniovi.es", "A2", "Oviedo", "Persona", 2);
		agente3=new Agent("Agente3", "agente3", "agente3@uniovi.es", "A3", "Oviedo", "Persona", 2);	
	}
	
	@Test
	public void testEmail() 
	{
		assertTrue(agente1.getEmail().equals("agente1@uniovi.es"));
		assertTrue(agente2.getEmail().equals("agente2@uniovi.es"));
		assertTrue(agente3.getEmail().equals("agente3@uniovi.es"));
		agente1.setEmail("nuevo1@uniovi.es");
		agente2.setEmail("nuevo2@uniovi.es");
		agente3.setEmail("nuevo3@uniovi.es");
		assertTrue(agente1.getEmail().equals("nuevo1@uniovi.es"));
		assertTrue(agente2.getEmail().equals("nuevo2@uniovi.es"));
		assertTrue(agente3.getEmail().equals("nuevo3@uniovi.es"));
	}
	
	@Test
	public void testNombre()
	{
		assertTrue(agente1.getNombre().equals("Agente1"));
		assertTrue(agente2.getNombre().equals("Agente2"));
		assertTrue(agente3.getNombre().equals("Agente3"));
		agente1.setNombre("Pepe");
		agente2.setNombre("Manolo");
		agente3.setNombre("Facundo");
		assertTrue(agente1.getNombre().equals("Pepe"));
		assertTrue(agente2.getNombre().equals("Manolo"));
		assertTrue(agente3.getNombre().equals("Facundo"));
	}
	
	@Test
	public void testIdenficador()
	{
		assertTrue(agente1.getIdentificador().equals("A1"));
		assertTrue(agente2.getIdentificador().equals("A2"));
		assertTrue(agente3.getIdentificador().equals("A3"));
		agente1.setIdentificador("A4");
		agente2.setIdentificador("A5");
		agente3.setIdentificador("A6");
		assertTrue(agente1.getIdentificador().equals("A4"));
		assertTrue(agente2.getIdentificador().equals("A5"));
		assertTrue(agente3.getIdentificador().equals("A6"));
	}
	
	@Test
	public void testKindCode()
	{
		assertTrue(agente1.getKindCode()==2);
		assertTrue(agente2.getKindCode()==2);
		assertTrue(agente3.getKindCode()==2);
		agente1.setKindCode(3);
		agente2.setKindCode(1);
		agente3.setKindCode(3);
		assertTrue(agente1.getKindCode()==3);
		assertTrue(agente2.getKindCode()==1);
		assertTrue(agente3.getKindCode()==3);
	}
	
	@Test
	public void testTipo()
	{
		assertTrue(agente1.getKind().equals("Persona"));
		assertTrue(agente2.getKind().equals("Persona"));
		assertTrue(agente3.getKind().equals("Persona"));
		agente1.setKind("Sensor");
		agente2.setKind("Molino");
		agente3.setKind("Radar");
		assertTrue(agente1.getKind().equals("Sensor"));
		assertTrue(agente2.getKind().equals("Molino"));
		assertTrue(agente3.getKind().equals("Radar"));
	}
	
	
	

}
