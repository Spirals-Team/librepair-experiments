
package coaching.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import org.junit.AssumptionViolatedException;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import static org.junit.Assume.assumeNotNull;

public class UnitTest {

    public class ClassUnderTest {
        public ClassUnderTest(final Object object) {
        }

        public void doSomething(final Object state) {
        }

        public Object passed() {
            return null;
        }
    }

    @Test
    public void testTypicalUsage() {
        // Given
        final Object state = new Object();
        assumeNotNull(state);

        // When
        final ClassUnderTest classUnderTest = new ClassUnderTest(null);
        classUnderTest.doSomething(state);

        // Then
        final Object expected = null;
        assertThat(classUnderTest.passed(), is(equalTo(expected)));
    }

    @Test
    public void testTypicalPassAssumeGiven() {
        // Given
        final Object state = new Object();
        assumeNotNull(state);

        // When
        final ClassUnderTest classUnderTest = new ClassUnderTest(state);
        classUnderTest.doSomething(state);

        // Then
        final Object expected = null;
        assertThat(classUnderTest.passed(), is(equalTo(expected)));
    }

    @Test
    public void testTypicalPassAssertGiven() {
        // Given
        final Object state = new Object();
        assertNotNull(state);

        // When
        final ClassUnderTest classUnderTest = new ClassUnderTest(state);
        classUnderTest.doSomething(state);

        // Then
        final Object expected = null;
        assertThat(classUnderTest.passed(), is(equalTo(expected)));
    }

    @Test(expected = AssumptionViolatedException.class)
    public void testTypicalFailAssumeGiven() {
        // Given
        final Object state = null;
        assumeNotNull(state);
        // When
        // Then
    }

    @Test(expected = AssertionError.class)
    public void testTypicalFailAssertGiven() {
        // Given
        final Object state = null;
        assertNotNull(state);
        // When
        // Then
    }

}
