package nc.noumea.mairie.radi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;

public class HelperTest {

	@Test
	public void formatActiveDirectoryLongDateTime_StringIsNullOrEmpty() {
		// Given
		String timeStamp = null;
		
		// When
		Date result = Helper.fromActiveDirectoryLongDateTime(timeStamp);
		
		assertNull(result);
	}
	
	@Test
	public void formatActiveDirectoryLongDateTime_StringvaidTimeStamp() {
		// Given
		String timeStamp = "130088309250000000";
		
		// When
		Date result = Helper.fromActiveDirectoryLongDateTime(timeStamp);
		
		assertEquals(new DateTime(2013, 03, 27, 15, 8, 45).getMillis(), result.getTime());
	}
	
	@Test
	public void formatActiveDirectoryStringDateTime_StringIsDateFormat() {
		// When
		String timestamp = "20130320203107.0Z";
		
		// When
		Date result = Helper.fromActiveDirectoryStringDateTime(timestamp);
		
		assertEquals(new DateTime(2013, 03, 20, 7, 31, 20).getMillis(), result.getTime());
	}
}
