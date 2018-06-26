
package patterns.interpreter;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * class ContextTest.
 */
public class ContextTest {

    /**
     * Unit Test to context.
     */
    @Test
    public void testContext() {
        assertNotNull("Value cannot be null", new Context());
    }

}
