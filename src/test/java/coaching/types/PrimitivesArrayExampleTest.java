
package coaching.types;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

import coaching.arrays.NativeTypesArray;

/**
 * Unit Tests for Primitives Arrays.
 */
public class PrimitivesArrayExampleTest {

    /**
     * Unit Test to display array.
     */
    @Test
    public void testDisplay() {
        final NativeTypesArray primitivesArray = new NativeTypesArray();
        assertNotNull(primitivesArray);
        primitivesArray.display();
    }

    /**
     * Unit Test to display array.
     */
    @Test
    public void testDisplayMatrix() {
        final NativeTypesArray primitivesArray = new NativeTypesArray();
        assertNotNull(primitivesArray);
        primitivesArray.displayMatrix();
    }

    /**
     * Unit Test to display array.
     */
    @Test
    public void testDisplayArray() {
        final NativeTypesArray primitivesArray = new NativeTypesArray();
        assertNotNull(primitivesArray);
        primitivesArray.iterateArray();
    }
}
