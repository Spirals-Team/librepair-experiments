
package coaching.thread;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * class MainAppTest.
 */
public class MainAppTest {

    /**
     * Unit Test to main app.
     */
    @Test
    public void testMainApp() {
        final Application mainApp = new Application();
        assertNotNull(mainApp);
    }

}
