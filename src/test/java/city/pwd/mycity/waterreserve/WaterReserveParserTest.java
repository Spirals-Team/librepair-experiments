package city.pwd.mycity.waterreserve;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.h2o.json.utility.GsonUtility;
import city.pwd.mycity.waterreserve.bean.WaterReserveInfo;
import java.io.IOException;

public class WaterReserveParserTest {

	/**
	 * Rigourous Test :-)
	 * 
	 * @throws IOException
	 */
	@Test
	public void testLastUpdatedDate() throws IOException {
		WaterReserveInfo bean = null;
		WaterReserveParser parser = new WaterReserveParser();
		try {
			parser.waterReserveBetween("01/12/2003", parser.todayDateString());
			bean = GsonUtility.jsonToObject();
		} catch (IOException e) {
			throw e;
		}
		assertEquals(bean.lastUpdatedDate, parser.todayDateString());
	}
}
