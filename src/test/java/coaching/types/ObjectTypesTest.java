
package coaching.types;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * The ObjectTypesTest Class.
 */
public class ObjectTypesTest {

    /**
     * Unit Test to display.
     */
    @Test
    public void testDisplay() {
        final ObjectTypes objectTypes = new ObjectTypes();
        assertNotNull("Value cannot be null", objectTypes);
        objectTypes.display();
    }

}
