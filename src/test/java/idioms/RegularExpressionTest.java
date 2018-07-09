
package idioms;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Regular Expression Test Class.
 */
public class RegularExpressionTest {
	private static final String PATTERN_STRING = "^XYZ$";
	private final Pattern PATTERN = Pattern.compile(PATTERN_STRING);

	@Test
	public void testRegularExpression() {
		final RegularExpression regularExpression = new RegularExpression();
		assertNotNull(regularExpression);
	}

	@Test
	public void testRegularExpressionString() {
		final RegularExpression regularExpression = new RegularExpression(PATTERN_STRING);
		assertNotNull(regularExpression);
	}

	@Test
	public void testRegularExpressionPattern() {
		final RegularExpression regularExpression = new RegularExpression(this.PATTERN);
		assertNotNull(regularExpression);
	}

	@Test
	public void testVerify() {
		final RegularExpression regularExpression = new RegularExpression();
		assertNotNull(regularExpression);
		assertTrue(regularExpression.verify("ABC"));
	}

	@Test
	public void testFind() {
		final RegularExpression regularExpression = new RegularExpression();
		assertNotNull(regularExpression);
		regularExpression.find("ABC");
	}

	@Test
	public void testReplace() {
		final RegularExpression regularExpression = new RegularExpression();
		assertNotNull(regularExpression);
		regularExpression.replace("ABC", "ZYZ");
	}

}
