
package patterns.chain;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * The ChainOfResponsibilityTest class.
 */
public class ChainOfResponsibilityTest {

    /**
     * Unit Test to handle request.
     */
    @Test
    public void testHandleRequest() {
        final AbstractHandler one = new HandlerOne();
        assertNotNull(one);

        final AbstractHandler two = new HandlerTwo();
        assertNotNull(two);

        final Request request = new Request("Payload");
        assertNotNull(request);
    }

}
