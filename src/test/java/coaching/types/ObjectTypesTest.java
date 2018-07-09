
package coaching.types;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * The ObjectTypesTest class.
 */
public class ObjectTypesTest {

    /**
     * Unit Test to display.
     */
    @Test
    public void testDisplay() {
        final ObjectTypes objectTypes = new ObjectTypes();
        assertNotNull(objectTypes);
        objectTypes.display();
    }

}
