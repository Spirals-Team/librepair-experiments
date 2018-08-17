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
public class CounterTest {
	/**
	 * Test от 1 до 10.
	 */
	@Test
    public void whenValuesFromOneToTenThenThirty() {
        Counter count = new Counter();
        int value = count.add(1, 10);
        assertThat(value, is(30));
   }
}
