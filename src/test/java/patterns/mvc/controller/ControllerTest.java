
package patterns.mvc.controller;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * The ControllerTest Class.
 */
public class ControllerTest {

    /**
     * Unit Test to controller.
     *
     */
    @Test
    public void testController() {
        final Controller controller = new Controller();
        assertNotNull("Value cannot be null", controller);
    }
}
