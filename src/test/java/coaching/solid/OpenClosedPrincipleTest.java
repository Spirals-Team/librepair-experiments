
package coaching.solid;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Unit Test for the Open Closed Principle (OCP) example class.
 */
public class OpenClosedPrincipleTest {

    /**
     * Unit Test to open closed principle example.
     */
    @Test
    public void testOpenClosedPrinciple() {
        final OpenClosedPrinciple ocp = new OpenClosedPrinciple();
        assertNotNull(ocp);
    }

}
