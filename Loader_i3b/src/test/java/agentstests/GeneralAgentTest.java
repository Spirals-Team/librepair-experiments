package agentstests;

import static org.junit.Assert.*;

import org.junit.Test;

import es.uniovi.asw.parser.agents.GeneralAgent;

public class GeneralAgentTest {

	@Test
	public void testEquals() {
		
		GeneralAgent dummy = new GeneralAgent("a", "45,-1", "pruebaAA@email.com", "12345678A", "4");
		GeneralAgent dummy0 = new GeneralAgent("a", "45,-1", "pruebaAA@email.com", "12345678A", "4");
		GeneralAgent dummy1 = new GeneralAgent("b", "45,-1", "prueba@email.com", "12345678B", "4");
		GeneralAgent dummy2 = new GeneralAgent("c", "45,-1", "blabala@email.com", "12345678C", "4");
		GeneralAgent dummy3 = new GeneralAgent("a", "45,-1", "prueba@email.com", "12345678D", "4");

		assertTrue(dummy.equals(dummy0));
		assertTrue(dummy.equals(dummy));
		assertFalse(dummy.equals(dummy1));
		assertFalse(dummy.equals(dummy2));
		assertFalse(dummy.equals(dummy3));
	}

}
