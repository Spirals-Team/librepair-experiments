package ru.job4j;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
* Test.
* @author Yury Matskevich (y.n.matskevich@gmail.com)
* @version $Id$
*/

public class CalculateTest {
	/**
	* Test echo.
	*/ 
	@Test
	public void whenTakeNameThenTreeEchoPlusName() {
		String input = "Yury Matskevich";
		String expect = "Echo, echo, echo : Yury Matskevich"; 
		Calculate calc = new Calculate();
		String result = calc.echo(input);
		assertThat(result, is(expect));
	}
}