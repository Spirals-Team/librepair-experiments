package ru.job4j;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
* Test.
*
* @author Michael Hodkov
* @version $Id$
* @since 0.1
*/
public class CalculateTest {

	/**
	* Тест сложения.
	*/
	@Test
	public void whenArgumentOnePlusArgumentTwo() {
		Calculate calc = new Calculate();
		calc.add(3D, 5D);
		double result = calc.getResult();
		double expected = 8D;
		assertThat(result, is(expected));
	}

	/**
	 * Тест вычитания.
	 */
	@Test
	public void whenArgumentOneMinusArgumentTwo() {
		Calculate calc = new Calculate();
		calc.subtract(3D, 5D);
		double result = calc.getResult();
		double expected = -2D;
		assertThat(result, is(expected));
	}

	/**
	 * Тест деления.
	 */
	@Test
	public void whenArgumentOneDivArgumentTwo() {
		Calculate calc = new Calculate();
		calc.div(9D, 3D);
		double result = calc.getResult();
		double expected = 3D;
		assertThat(result, is(expected));
	}

	/**
	 * Тест умножения.
	 */
	@Test
	public void whenArgumentOneMultipleArgumentTwo() {
		Calculate calc = new Calculate();
		calc.multiple(3D, 5D);
		double result = calc.getResult();
		double expected = 15D;
		assertThat(result, is(expected));
	}
}