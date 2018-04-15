package aserdyuchenko;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.apache.commons.io.output.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Тест для класса Calculate.
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 28.08.2016
 */
public class CalculateTest {
/**
 * whenYouWantSeeHelloWorld.
 */
    @Test
    public void whenYouWantSeeHelloWorld() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
        Calculate hellow = new Calculate();
        hellow.hello("");
        assertThat(out.toString(), is(""));
     }
}
