package ru.job4j.loop;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
* Test.
* @author Igor Fedorenko (mailto:if.zommy@gmail.com)
* @version $Id$
* @since 0.1
*/
public class FactorialTest {
	/**
	 * Test для n = 5.
	 */
	@Test
    public void whenValuesFiveThenOneHundredTwenty() {
        Factorial fact = new Factorial();
        int value = fact.calc(5);
        assertThat(value, is(120));
   }
	/**
	 * Test для n = 0.
	 */
	 public void whenValuesZeroThenOne() {
        Factorial fact = new Factorial();
        int value = fact.calc(0);
        assertThat(value, is(1));
	}
}