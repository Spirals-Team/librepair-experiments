
package patterns.router;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * class RouteTest.
 */
public class RouteTest {

    /**
     * class InputChanel.
     */
    public final class InputChanel extends AbstractInputChannel {
    }

    /**
     * class OutputChannel.
     */
    public final class OutputChannel extends AbstractOutputChannel {
    }

    /**
     * Unit Test to route.
     */
    @Test
    public void testRoute() {
        final OutputChannel outputChannel = new OutputChannel();
        final InputChanel inputChannel = new InputChanel();
        final Route route = new Route(outputChannel, inputChannel);
        assertNotNull(route);
    }

}
