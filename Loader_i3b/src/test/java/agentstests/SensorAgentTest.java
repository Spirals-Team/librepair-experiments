package agentstests;

import static org.junit.Assert.*;

import org.junit.Test;

import es.uniovi.asw.parser.agents.SensorAgent;

public class SensorAgentTest {

	@Test
	public void testEquals() {
		
		SensorAgent dummy = new SensorAgent("a", "45,-1", "pruebaAA@email.com", "12345678A", "3");
		SensorAgent dummy0 = new SensorAgent("a", "45,-1", "pruebaAA@email.com", "12345678A", "3");
		SensorAgent dummy1 = new SensorAgent("b", "45,-1", "prueba@email.com", "12345678B", "3");
		SensorAgent dummy2 = new SensorAgent("c", "45,-1", "blabala@email.com", "12345678C", "3");
		SensorAgent dummy3 = new SensorAgent("a", "45,-1", "prueba@email.com", "12345678D", "3");

		assertTrue(dummy.equals(dummy0));
		assertTrue(dummy.equals(dummy));
		assertFalse(dummy.equals(dummy1));
		assertFalse(dummy.equals(dummy2));
		assertFalse(dummy.equals(dummy3));
	}

}
