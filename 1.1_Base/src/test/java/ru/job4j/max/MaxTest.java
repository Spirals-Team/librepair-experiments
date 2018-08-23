package ru.job4j.max;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
* Test.
* @author Igor Fedorenko (mailto:if.zommy@gmail.com)
* @version $Id$
* @since 0.1
*/
public class MaxTest {
	/**
	 * Test first less second.
	 */
	@Test
	public void whenFirstLessSecond() {
		Max maxim = new Max();
		int result = maxim.max(1, 2);
		assertThat(result, is(2));
	}
	/**
	 * Test first more second.
	 */
	@Test
	public void whenFirstMoreSecond() {
		Max maxim = new Max();
		int result = maxim.max(3, 2);
		assertThat(result, is(3));
	}
	/**
	 * Test first equal second.
	 */
	@Test
	public void whenFirstEqualSecond() {
		Max maxim = new Max();
		int result = maxim.max(1, 1);
		assertThat(result, is(1));
	}
	/**
	 * Test first more second and third value.
	 */
	@Test
	public void whenFirstMoreSecondAndThird() {
		Max maxim = new Max();
		int result = maxim.max(3, 2, 1);
		assertThat(result, is(3));
	}
	/**
	 * Test second more first and third value.
	 */
	@Test
	public void whenSecondMoreFirstAndThird() {
		Max maxim = new Max();
		int result = maxim.max(2, 4, 2);
		assertThat(result, is(4));
	}
	/**
	 * Test third more first and second value.
	 */
	@Test
	public void whenThirdMoreFirstAndSecond() {
		Max maxim = new Max();
		int result = maxim.max(3, 5, 7);
		assertThat(result, is(7));
	}
}