
package patterns.visitor;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Unit Test for Visitor class.
 *
 * Represent an operation to be performed on elements of an object structure.
 */
public class VisitorTest {

    /**
     * Unit Test to operation.
     */
    @Test
    public void testOperation() {
        final Visitor visitor = new Visitor();
        assertNotNull("Value cannot be null", visitor);

        final Element element = new Element();
        assertNotNull("Value cannot be null", element);
    }

}
