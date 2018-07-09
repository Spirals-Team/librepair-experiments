
package patterns.mediator;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * class MediatorTest.
 */
public class MediatorTest {

    /**
     * Unit Test to.
     */
    @Test
    public void test() {
        final Mediator mediator = new Mediator();
        assertNotNull(mediator);
    }
}
