package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class IncidenceTest {

	@Test
	public void testIncidence() {
		Incidence inci = new Incidence();
		assertNotNull(inci.getComments());
		assertNotNull(inci.getTags());
		assertNotNull(inci.getCustomFields());
	}
	@Test
	public void testIncidence2() {
		List<String> tags = new ArrayList<String>();
		List<String> otherfields = new ArrayList<String>();
		List<String> comments = new ArrayList<String>();

		Incidence inci = new Incidence();

		/*Test setters*/
		inci.setInciId("Id2");
		assertEquals("Id2", inci.getInciId());

		inci.setUsername("Tsunami");
		assertEquals("Tsunami", inci.getUsername());

		inci.setUsertype(3);
		assertEquals(3, inci.getUsertype());

		inci.setInci_name("a");
		assertEquals("a", inci.getInci_name());

		inci.setInci_description("b");
		assertEquals("b", inci.getInci_description());

		inci.setInci_location("c");
		assertEquals("c", inci.getInci_location());

		inci.setInci_info("d");
		assertEquals("d", inci.getInci_info());

		inci.setOperatorId("e");
		assertEquals("e", inci.getOperatorId());

		
	}

}
