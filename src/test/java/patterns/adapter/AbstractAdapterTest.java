
package patterns.adapter;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * AbstractAdapter class test.
 */
public class AbstractAdapterTest {

    public class TestMockAdapter extends AbstractAdapter {
    }

    @Test
    public void type() {
        assertThat(AbstractAdapter.class, notNullValue());
    }

    @Test
    public void instantiation() {
        final AbstractAdapter target = new TestMockAdapter();
        assertThat(target, notNullValue());
    }

    @Test
    public void request_A$() {
        final AbstractAdapter target = new TestMockAdapter();
        final Result actual = target.request();
        final Result expected = null;
        assertThat(actual, is(equalTo(expected)));
    }

}
