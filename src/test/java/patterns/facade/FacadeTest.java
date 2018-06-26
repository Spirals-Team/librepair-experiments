
package patterns.facade;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * class FacadeTest.
 */
public class FacadeTest {

    /**
     * Unit Test to concrete facade operation.
     */
    @Test
    public void testConcreteFacadeOperation() {
        assertNotNull("Value cannot be null", new ConcreteFacade());
    }

}
