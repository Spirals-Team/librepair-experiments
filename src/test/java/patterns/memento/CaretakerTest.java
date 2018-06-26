
package patterns.memento;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class CaretakerTest {

    @Test
    public void type() {
        assertThat(Caretaker.class, notNullValue());
    }

    @Test
    public void instantiation() {
        final Caretaker target = new Caretaker();
        assertThat(target, notNullValue());
    }

}
