
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
        assertNotNull("Value cannot be null", context);
        final StrategyInterface strategyAlice = new StrategyAlice(context);
        assertNotNull("Value cannot be null", strategyAlice);
        final StrategyInterface strategyBob = new StrategyBob(context);
        assertNotNull("Value cannot be null", strategyBob);
        final StrategyInterface strategyCharlie = new StrategyCharlie(context);
        assertNotNull("Value cannot be null", strategyCharlie);
    }
}
