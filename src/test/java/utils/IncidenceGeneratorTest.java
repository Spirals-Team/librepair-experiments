package utils;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Incidence;

public class IncidenceGeneratorTest {

	@Test
	public void test() {
		Incidence inci = IncidenceUtils.randomInci(1);
		assertNotNull(inci.getInciId());
		assertNotNull(inci.getUsername());
		assertNotNull(inci.getUsertype());
		assertNotNull(inci.getInciName());
		assertNotNull(inci.getInciDescription());
		assertNotNull(inci.getInciLocation());
		assertNotNull(inci.getInciInfo());
		assertNotNull(inci.getState());
		assertNotNull(inci.getExpiration());
		assertNotNull(inci.getOperatorId());


		
		}

}
