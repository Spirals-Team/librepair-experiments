package idioms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class EnumExampleTest {

	@Test
	public void testEnumExample() {
		final EnumExample enumExample = EnumExample.Unknown;
		assertNotNull(enumExample);
	}

	@Test
	public void testFromString() {
		final EnumExample enumExample = EnumExample.Unknown;
		assertNotNull(enumExample);
		assertEquals("Unknown", enumExample.toString());
	}

}
