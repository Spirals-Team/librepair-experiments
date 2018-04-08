package agentstests;

import static org.junit.Assert.*;

import org.junit.Test;

import es.uniovi.asw.parser.agents.PersonAgent;

public class PersonAgentTest {

	@Test
	public void testEquals() {
		
		PersonAgent dummy = new PersonAgent("a", "45,-1", "pruebaAA@email.com", "12345678A", "1");
		PersonAgent dummy0 = new PersonAgent("a", "45,-1", "pruebaAA@email.com", "12345678A", "1");
		PersonAgent dummy1 = new PersonAgent("b", "45,-1", "prueba@email.com", "12345678B", "1");
		PersonAgent dummy2 = new PersonAgent("c", "45,-1", "blabala@email.com", "12345678C", "1");
		PersonAgent dummy3 = new PersonAgent("a", "45,-1", "prueba@email.com", "12345678D", "1");

		assertTrue(dummy.equals(dummy0));
		assertTrue(dummy.equals(dummy));
		assertFalse(dummy.equals(dummy1));
		assertFalse(dummy.equals(dummy2));
		assertFalse(dummy.equals(dummy3));
	}

}
