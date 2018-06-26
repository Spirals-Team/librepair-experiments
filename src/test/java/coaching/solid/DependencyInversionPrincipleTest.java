
package coaching.solid;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Unit Test for the Dependency Inversion Principle example class.
 */
public class DependencyInversionPrincipleTest {

    /**
     * Unit Test to demonstrate the dependency inversion principle with java example.
     */
    @Test
    public void testDependencyInversionPrinciple() {
        final DependencyInversionPrinciple dip = new DependencyInversionPrinciple();
        assertNotNull("Value cannot be null", dip);
    }

}
