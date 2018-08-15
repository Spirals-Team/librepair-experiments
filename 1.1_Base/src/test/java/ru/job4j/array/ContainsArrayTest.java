package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
* Test Array contains.
* @author Igor Fedorenko (mailto:if.zommy@gmail.com)
* @version $Id$
* @since 0.1
*/
public class ContainsArrayTest {
	/**
	* return true.
	*/
	@Test
	public void whenOriginStringHelloAndSubStringEllThenReturnTrue() {
        ContainsArray cont = new ContainsArray();
		String origin = "Hello";
		String sub = "ell";
		boolean result = cont.contains(origin, sub);
		boolean expect = true;
		assertThat(result, is(expect));
	}

    /**
     * return false.
     */
    @Test
    public void whenOriginStringHelloAndSubStringHllThenReturnTrue() {
        ContainsArray cont = new ContainsArray();
        String origin = "Hello";
        String sub = "Hll";
        boolean result = cont.contains(origin, sub);
        boolean expect = false;
        assertThat(result, is(expect));
    }
}