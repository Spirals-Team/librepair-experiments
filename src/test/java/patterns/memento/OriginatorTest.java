
package patterns.memento;

import static org.hamcrest.CoreMatchers.notNullValue;

import java.util.UUID;

import org.junit.Test;

import static org.junit.Assert.assertThat;

public class OriginatorTest {

    @Test
    public void testTypicalUsage() {
        // Given
        final String stateOne = UUID.randomUUID().toString();
        final String stateTwo = UUID.randomUUID().toString();

        // When
        final Originator originator = new Originator(stateOne);
        originator.createMemento(stateTwo);
        originator.revert();

        // Then
    }

    @Test
    public void type() {
        assertThat(Originator.class, notNullValue());
    }

    @Test
    public void instantiation() {
        final Object state = null;
        final Memento memento = null;
        final Originator target = new Originator(state, memento);
        assertThat(target, notNullValue());
    }

    @Test
    public void createMemento_A$() {
        // Given
        final Object state = null;
        final Memento memento = null;
        final Originator originator = new Originator(state, memento);

        // When
        final Memento actual = originator.createMemento();

        // Then
    }

    @Test
    public void revert_A$() {
        // Given
        final Object state = UUID.randomUUID().toString();
        final Memento memento = new Memento(state);
        final Originator originator = new Originator(state, memento);

        // When
        originator.createMemento();

        // Then
        originator.revert();

    }

}
