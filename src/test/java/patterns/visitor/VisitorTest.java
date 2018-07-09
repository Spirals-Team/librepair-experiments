
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
        final ConcreteVisitorOne visitorOne = new ConcreteVisitorOne();
        assertNotNull(visitorOne);

        final ConcreteVisitorTwo visitorTwo = new ConcreteVisitorTwo();
        assertNotNull(visitorTwo);

        final ConcreteElement element = new ConcreteElement();
        assertNotNull(element);
    }

}
