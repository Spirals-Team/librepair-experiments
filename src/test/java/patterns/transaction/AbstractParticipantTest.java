
package patterns.transaction;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * AbstractParticipant class tests.
 */
public class AbstractParticipantTest {

    public class TestMockParticipant extends AbstractParticipant {
        @Override
        public ParticipantInterface operation1() {
            return this;
        }

        @Override
        public ParticipantInterface operation2() {
            return this;
        }

        @Override
        public ParticipantInterface join() {
            return this;
        }

        @Override
        public ParticipantInterface commit() {
            return this;
        }

        @Override
        public ParticipantInterface cancel() {
            return this;
        }

    }

    @Test
    public void testTypicalUsageCommit() {
        // Given
        final AbstractParticipant target = new TestMockParticipant();
        target.join();

        // When
        target.operation1();
        target.operation2();

        // Then
        target.commit();
    }

    @Test
    public void testTypicalUsageCancel() {
        // Given
        final AbstractParticipant target = new TestMockParticipant();
        target.join();

        // When
        target.operation1();
        target.operation2();

        // Then
        target.cancel();
    }

    @Test
    public void type() {
        assertThat(AbstractParticipant.class, notNullValue());
    }

}
