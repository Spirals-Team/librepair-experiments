package agentstests;

import static org.junit.Assert.*;

import org.junit.Test;

import es.uniovi.asw.parser.agents.EntityAgent;

public class EntityAgentTest {

	@Test
	public void testEquals() {
		
		EntityAgent dummy = new EntityAgent("a", "45,-1", "pruebaAA@email.com", "12345678A", "2");
		EntityAgent dummy0 = new EntityAgent("a", "45,-1", "pruebaAA@email.com", "12345678A", "2");
		EntityAgent dummy1 = new EntityAgent("b", "45,-1", "prueba@email.com", "12345678B", "2");
		EntityAgent dummy2 = new EntityAgent("c", "45,-1", "blabala@email.com", "12345678C", "2");
		EntityAgent dummy3 = new EntityAgent("a", "45,-1", "prueba@email.com", "12345678D", "2");

		assertTrue(dummy.equals(dummy0));
		assertTrue(dummy.equals(dummy));
		assertFalse(dummy.equals(dummy1));
		assertFalse(dummy.equals(dummy2));
		assertFalse(dummy.equals(dummy3));
	}

}
