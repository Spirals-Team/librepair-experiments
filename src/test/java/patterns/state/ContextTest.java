
package patterns.state;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Context class tests.
 */
public class ContextTest {

    @Test
    public void testTypicalUsage() {
        final AbstractState state = null;
        final Context target = new Context(state);
        assertThat(target, notNullValue());
    }

    @Test
    public void type() {
        assertThat(Context.class, notNullValue());
    }

    @Test
    public void instantiation() {
        final AbstractState state = null;
        final Context target = new Context(state);
        assertThat(target, notNullValue());
    }

    @Test
    public void testToAlice() {
        // Given
        final AbstractState state = null;
        final Context context = new Context(state);

        // When
        assertEquals(context.toAlice(), context);

        // Then
        final String expected = "Context [state=StateAlice]";
        assertThat(context.toString(), is(equalTo(expected)));
    }

    @Test
    public void testToBob() {
        // Given
        final AbstractState state = null;
        final Context context = new Context(state);

        // When
        assertEquals(context.toBob(), context);

        // Then
        final String expected = "Context [state=StateBob]";
        assertThat(context.toString(), is(equalTo(expected)));
    }

    @Test
    public void toString_A$() {
        // Given
        final AbstractState state = null;
        final Context target = new Context(state);

        // When
        final String actual = target.toString();

        // Then
        final String expected = "Context [state=null]";
        assertThat(actual, is(equalTo(expected)));
    }

}
