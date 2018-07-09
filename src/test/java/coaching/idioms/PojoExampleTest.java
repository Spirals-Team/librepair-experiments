
package coaching.idioms;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * PojoExampleTest class.
 */
@SuppressWarnings("SpellCheckingInspection")
public class PojoExampleTest {

    /**
     * Unit Test to pojo example.
     */
    @Test
    public void testPojoExample() {
        final PojoExample pojoExample = new PojoExample();
        assertNotNull(pojoExample);
    }

    /**
     * Unit Test to pojo example string long.
     */
    @Test
    public void testPojoExampleStringLong() {
        final PojoExample pojoExample = new PojoExample("Long", Long.MAX_VALUE);
        assertNotNull(pojoExample);
    }

    /**
     * Unit Test to set get string value.
     */
    @Test
    public void testSetGetStringValue() {
        final PojoExample pojoExample = new PojoExample();
        assertNotNull(pojoExample);
        final String expectedStringValue = "Long";
        pojoExample.setStringValue(expectedStringValue);
        final String stringValue = pojoExample.getStringValue();
        assertEquals(expectedStringValue, stringValue);
    }

    /**
     * Unit Test to set get long value.
     */
    @Test
    public void testSetGetLongValue() {
        final PojoExample pojoExample = new PojoExample();
        assertNotNull(pojoExample);
        final long expectedLongValue = Long.MAX_VALUE;
        pojoExample.setLongValue(expectedLongValue);
        final long longValue = pojoExample.getLongValue();
        assertEquals(expectedLongValue, longValue);
    }

}
