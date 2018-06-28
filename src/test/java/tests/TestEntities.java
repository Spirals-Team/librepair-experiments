package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.Test;

import inciManager.entities.Agente;
import inciManager.entities.Estado;
import inciManager.entities.Incidencia;
import inciManager.entities.Localizacion;
import inciManager.entities.Operator;


public class TestEntities {

	@Test
	public void testAgente() {
		Agente agente = new Agente();
		agente.setIdentificador("id");
		agente.setKind("agente");
		agente.setPassword("pass");
		assertEquals("id", agente.getIdentificador());
		assertEquals("agente", agente.getKind());
		assertEquals("pass", agente.getPassword());
		
		Agente agente2 = new Agente("id", "pass", "agente");
		
		assertTrue(agente.equals(agente2));
		
		assertNotEquals(agente.hashCode(), new Agente().hashCode());
		
		System.out.println(agente.toString());
	}
	
	@Test
	public void testOperator() {
		Operator operador = new Operator();
		operador.setEmail("id@hotmail.es");
		operador.setIncidenciasAsignadas(null);
		operador.setPassword("pass");
		assertEquals("id@hotmail.es", operador.getEmail());
		assertNull(operador.getIncidenciasAsignadas());
		assertEquals("pass", operador.getPassword());
		
		Operator operador2 = new Operator("id@hotmail.es", "pass", "pepe");
		operador.setName("pepe");
		
		assertTrue(operador.equals(operador2));
		
		assertNotEquals(operador.hashCode(), new Operator().hashCode());
		
		System.out.println(operador.toString());
	}
	
	@Test
	public void testLocalizacion() {
		Localizacion local = new Localizacion();
		
		local.setLatitud(2.2);
		local.setLongitud(4.5);
		
		assertTrue(local.getLatitud() == 2.2);
		assertTrue(local.getLongitud() == 4.5);
		
		Localizacion local2 = new Localizacion(2.2,4.5);
		
		assertTrue(local.equals(local2));
		
		assertNotEquals(local.hashCode(), new Localizacion().hashCode());
		
		System.out.println(local.toString());
	}
	
	@Test
	public void testIncidencia() {
		Incidencia inci = new Incidencia();
		
		inci.setAgenteAux(null);
		inci.setCampos( new HashMap<String,String>() );
		inci.setCamposLista("prueba/test,campo2/algo");
		inci.setDescripcion("incidencia desc");
		inci.setEstado(Estado.ABIERTA);
		inci.setEtiquetas(new HashSet<String>() );
		inci.setId(0);
		inci.setIdentificadorAgente("Agente");
		inci.setLocalizacion(null);
		inci.setName("inci name");
		inci.setOperadorAsignado(null);
		inci.setUrlMasInfo("si");
		
		assertNull(inci.getAgenteAux());
		assertNotNull(inci.getCampos());
		assertTrue(inci.getCampos().keySet().contains("prueba"));
		
		assertNotEquals(inci.hashCode(), new Incidencia().hashCode());
		
		System.out.println(inci.toString());
	}
	


}
