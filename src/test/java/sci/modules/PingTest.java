package sci.modules;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Collection of tests for the ping module
 * @author rlee287
 */
public class PingTest {
    /**
     * Test module "ping" has name including "Ping".
     */
    @Test
    public void pingModuleName() {
        Module pingModule = new PingModule();
        assertTrue(pingModule.getName().contains("Ping"));
    }
    /**
     * Test module "ping" to ensure that invoker is "ping".
     */
    @Test
    public void pingModuleInvoker() {
        Module pingModule = new PingModule();
        assertEquals(pingModule.getInvokers(),"ping");
    }

    /**
     * Test module "ping" to ensure that it returns "pong".
     */
    @Test
    public void pingModuleProcess() {
        Module pingModule = new PingModule();
        assertEquals(pingModule.process("No NullLens for The Room", null),"pong");
    }
}
