
package coaching.types;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

import coaching.arrays.ObjectTypesArray;

/**
 * The ObjectTypesArrayTest class.
 */
public class ObjectTypesArrayTest {

    /**
     * Unit Test to display.
     */
    @Test
    public void testDisplayObjectArray() {
        final ObjectTypesArray objectTypes = new ObjectTypesArray();
        assertNotNull(objectTypes);
        objectTypes.iterateArray();
    }

    /**
     * Unit Test to display.
     */
    @Test
    public void testDisplayMatrix() {
        final ObjectTypesArray objectTypes = new ObjectTypesArray();
        assertNotNull(objectTypes);
        objectTypes.displayMatrix();
    }

    /**
     * Unit Test to display.
     */
    @Test
    public void testDisplay() {
        final ObjectTypesArray objectTypes = new ObjectTypesArray();
        assertNotNull(objectTypes);
        objectTypes.display();
    }
}
