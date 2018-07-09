
package patterns.session;

import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Test;

import static org.junit.Assert.assertThat;

public class SessionTest {

    @Test
    public void type() {
        assertThat(Session.class, notNullValue());
    }

    @Test
    public void instantiation() {
        final Session target = new Session();
        assertThat(target, notNullValue());
    }

}
