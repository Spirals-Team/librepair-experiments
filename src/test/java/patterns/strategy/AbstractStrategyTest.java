
package patterns.strategy;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * class AbstractStrategyTest.
 */
public class AbstractStrategyTest {

    /**
     * Unit Test to operation.
     */
    @Test
    public void testOperation() {
        final Context context = new Context();
        assertNotNull(context);
        final StrategyInterface strategyAlice = new StrategyAlice(context);
        assertNotNull(strategyAlice);
        final StrategyInterface strategyBob = new StrategyBob(context);
        assertNotNull(strategyBob);
        final StrategyInterface strategyCharlie = new StrategyCharlie(context);
        assertNotNull(strategyCharlie);
    }
}
