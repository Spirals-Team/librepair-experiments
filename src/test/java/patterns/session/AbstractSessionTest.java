
package patterns.session;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import patterns.composite.CompositeTest;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeNotNull;

/**
 * AbstractSession class tests.
 */
public class AbstractSessionTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(CompositeTest.class);

    /**
     * Test Mock for AbstractSession.
     */
    public class TestMockSession extends AbstractSession {
    }

    @Test
    public void testTypicalUsage() {
        // Given
        final TestMockSession testSession = new TestMockSession();
        assumeNotNull(testSession);

        // When
        testSession.setToken("token");
        assertThat(testSession, notNullValue());

        // Then
        final String token = testSession.token();
        assertThat(token, is(equalTo("token")));
    }

    /**
     * Type.
     */
    @Test
    public void type() {
        assertThat(TestMockSession.class, notNullValue());
    }

    /**
     * Sets the token as string.
     */
    @Test
    public void testSetGetToken() {
        // Given
        final TestMockSession testSession = new TestMockSession();
        assumeNotNull(testSession);

        // When
        final String token = "token";
        testSession.setToken(token);

        // Then
        assertThat(token, is(equalTo("token")));
    }

    /**
     * Gets the uuid.
     */
    @Test
    public void testSetGetUuid() {
        // Given
        final TestMockSession testSession = new TestMockSession();
        assumeNotNull(testSession);

        // When
        final UUID actual = testSession.getUuid();

        // Then
        assertNotNull(actual);
    }

    /**
     * Unit test ToString.
     */
    @Test
    public void testToString() {
        // Given
        final TestMockSession testSession = new TestMockSession();
        assumeNotNull(testSession);

        // When
        final String actual = testSession.toString();

        // Then
        assertNotNull(actual);
        LOG.debug("toString = {}", actual);
    }

}
