
package patterns.proxy;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Proxy Subject Test Class.
 */
public class ProxySubjectTest {

    /**
     * Unit Test to request.
     */
    @Test
    public void testRequest() {
        final SubjectProxy proxySubject = new SubjectProxy();
        assertNotNull("Value cannot be null", proxySubject);
        proxySubject.request();
    }
}
