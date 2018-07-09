
package patterns.adapter;

import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import static org.junit.Assume.assumeNotNull;

/**
 * TargetAdapter class tests.
 */
public class TargetAdapterTest {

    @Test
    public void testTypicalUsage() {
        // Given
        final TargetAdapter targetAdapter = new TargetAdapter();
        assumeNotNull(targetAdapter);

        // When
        targetAdapter.request();

        // Then
    }

    @Test
    public void type() {
        assertThat(TargetAdapter.class, notNullValue());
    }

    @Test
    public void instantiation() {
        final TargetAdapter target = new TargetAdapter();
        assertThat(target, notNullValue());
    }

    @Test
    public void request_A$() {
        final TargetAdapter target = new TargetAdapter();
        final Result actual = target.request();
        assertNotNull(actual);
    }

}
