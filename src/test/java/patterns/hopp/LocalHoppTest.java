
package patterns.hopp;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNotNull;

/**
 * class LocalHoppTest.
 */
public class LocalHoppTest {

    @Test
    public void testTypicalUsage() {
        // Given
        final LocalHopp localHopp = new LocalHopp();
        assumeNotNull(localHopp);

        // When
        localHopp.localMethod();

        // Then
    }

    /**
     * Test type.
     */
    @Test
    public void testType() {
        assertNotNull(LocalHopp.class);
    }

    /**
     * Test remote method.
     */
    @Test
    public void testRemoteMethod() {
        final LocalHopp localHopp = new LocalHopp();
        assumeNotNull("Value cannot be null", localHopp);
        localHopp.remoteMethod();
    }

    /**
     * Test local method.
     */
    @Test
    public void testLocalMethod() {
        final LocalHopp localHopp = new LocalHopp();
        assumeNotNull("Value cannot be null", localHopp);
        localHopp.localMethod();
    }

}
