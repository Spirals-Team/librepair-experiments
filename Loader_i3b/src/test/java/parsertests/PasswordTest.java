package parsertests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import es.uniovi.asw.parser.agents.AbstractAgent;
import es.uniovi.asw.parser.agents.PersonAgent;
import es.uniovi.asw.parser.parserutil.PasswordGenerator;

public class PasswordTest {

	@Test
	public void testNotNull() {
		PersonAgent c = new PersonAgent("Santi", "Samuel Sanchez", "xd@xd.com", "id5", "1");
		Set<AbstractAgent> census = new HashSet<AbstractAgent>();
		census.add(c);
		PasswordGenerator.createPasswords(census);
		assertTrue(c.getPassword() != null);

		PasswordGenerator pass = new PasswordGenerator();
		assertNotNull(pass);
	}

}
