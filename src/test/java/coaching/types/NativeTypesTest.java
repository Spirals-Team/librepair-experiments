
package coaching.types;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Class showing usage of native types.
 */
public class NativeTypesTest {

    /**
     * Unit Test to display.
     */
    @Test
    public void testDisplay() {
        final NativeTypes nativeTypes = new NativeTypes();
        assertNotNull(nativeTypes);
        nativeTypes.display();
    }

}
