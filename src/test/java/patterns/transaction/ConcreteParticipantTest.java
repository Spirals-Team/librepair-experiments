
package patterns.transaction;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * class ConcreteParticipantTest.
 */
public class ConcreteParticipantTest {

    /**
     * Unit Test to commit.
     */
    @Test
    public void testCommit() {
        final ConcreteParticipant testInstance = new ConcreteParticipant();
        assertNotNull("Value cannot be null", testInstance);
    }

}
