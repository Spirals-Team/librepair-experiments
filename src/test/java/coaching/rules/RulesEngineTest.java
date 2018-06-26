
package coaching.rules;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * The RulesEngineTest Class.
 */
public class RulesEngineTest {
    
    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(RulesEngineTest.class);

    /**
     * Unit Test to rules engine.
     */
    @Test
    public void testRulesEngine() {
        LOG.info("testRulesEngine();");
        final RulesEngine rulesEngine = new RulesEngine();
        assertNotNull("Value cannot be null", rulesEngine);
    }

    /**
     * Unit Test to execute.
     */
    @Test
    public void testExecute() {
        LOG.info("new RulesEngine().execute();");
        final RulesEngine rulesEngine = new RulesEngine();
        assertNotNull("Value cannot be null", rulesEngine.execute());
    }

}
