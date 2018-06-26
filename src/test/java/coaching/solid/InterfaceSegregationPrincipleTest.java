
package coaching.solid;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Unit Test for the Interface Segregation Principle (ISP) example class.
.
 */
public class InterfaceSegregationPrincipleTest {

    /**
     * Unit Test for the interface segregation principle example java example class.
     */
    @Test
    public void testInterfaceSegregationPrinciple() {
        final InterfaceSegregationPrinciple isp = new InterfaceSegregationPrinciple();
        assertNotNull("Value cannot be null", isp);
    }

}
