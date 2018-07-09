
package patterns.session;

import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import static org.junit.Assume.assumeNotNull;

/**
 * SessionTracker class tests.
 */
public class SessionTrackerTest {

    private static final Logger LOG = LoggerFactory.getLogger(SessionTrackerTest.class);

    @Test
    public void testTypicalUsage() {
        // Given
        final SessionTracker sessionTracker = new SessionTracker();
        assumeNotNull(sessionTracker);
        LOG.info(sessionTracker.toString());

        // When
        final AbstractSession session = sessionTracker.createSession();
        session.setToken("TestToken");
        assertNotNull(session);
        LOG.info(session.toString());

        // Then
        assertEquals("TestToken", session.token());
        sessionTracker.destroySession(session);
        LOG.info(sessionTracker.toString());
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
