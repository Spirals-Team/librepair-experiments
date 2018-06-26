
package patterns.hopp;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNotNull;

/**
 * RemoteObjectTest class.
 */
public class RemoteObjectTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(RemoteObjectProxy.class);

    @Test
    public void testTypicalUsage() {
        // Given
        final RemoteObject remoteObject = new RemoteObject();
        assumeNotNull(remoteObject);

        // When
        remoteObject.remoteMethod();

        // Then
    }

    /**
     * Unit test the type.
     */
    @Test
    public void testType() {
        assertNotNull(RemoteObject.class);
    }

    /**
     * Unit test to remote method a$.
     */
    @Test
    public void testRemoteMethod() {
        final RemoteObject remoteObject = new RemoteObject();
        assumeNotNull("Value cannot be null", remoteObject);
        remoteObject.remoteMethod();
    }

    /**
     * Unit test to local method a$.
     */
    @Test
    public void testLocalMethod() {
        final RemoteObject remoteObject = new RemoteObject();
        assumeNotNull("Value cannot be null", remoteObject);
        remoteObject.localMethod();
    }

}
