package utils;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Incidence;

public class IncidenceUtilsTest {

	@Test
	public void test() {
		assertEquals("Open", IncidenceUtils.getStateString(0));
		assertEquals("In process", IncidenceUtils.getStateString(1));
		assertEquals("Closed", IncidenceUtils.getStateString(2));
		assertEquals("Cancelled", IncidenceUtils.getStateString(3));
		assertEquals("", IncidenceUtils.getStateString(4));



		
		}

}
