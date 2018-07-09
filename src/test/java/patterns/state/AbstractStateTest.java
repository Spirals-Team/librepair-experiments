
package patterns.state;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * class AbstractStateTest.
 */
public class AbstractStateTest {

    /**
     * Unit Test to abstract state.
     */
    @Test
    public void testAbstractState() {
        final StateAlice stateAlice = new StateAlice();
        assertNotNull(stateAlice);
        final StateBob stateBob = new StateBob();
        assertNotNull(stateBob);
    }

}
