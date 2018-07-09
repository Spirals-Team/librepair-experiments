
package coaching.idioms;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The EnumExampleTest class.
 */
public class EnumExampleTest {

    /**
     * Unit Test to enum example.
     */
    @Test
    public void testEnumExample() {
        final EnumExample enumExample = EnumExample.Unknown;
        assertNotNull(enumExample);
    }

    /**
     * Unit Test to from string.
     */
    @Test
    public void testFromString() {
        final EnumExample enumExample = EnumExample.Unknown;
        assertNotNull(enumExample);
        assertEquals("Unknown", enumExample.toString());
    }

}
