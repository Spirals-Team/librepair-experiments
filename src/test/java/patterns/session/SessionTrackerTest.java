
package patterns.session;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeNotNull;

/**
 * SessionTracker class tests.
 */
public class SessionTrackerTest {

    @Test
    public void testTypicalUsage() {
        // Given
        final SessionTracker sessionTracker = new SessionTracker();
        assumeNotNull(sessionTracker);

        // When
        final AbstractSession session = sessionTracker.createSession();

        // Then
        sessionTracker.destroySession(session);
    }

    @Test
    public void type() {
        assertThat(SessionTracker.class, notNullValue());
    }

    @Test
    public void instantiation() {
        final SessionTracker target = new SessionTracker();
        assertThat(target, notNullValue());
    }

}
