package ru.job4j.array;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
* Test Ritate Array.
* @author Igor Fedorenko (mailto:if.zommy@gmail.com)
* @version $Id$
* @since 0.1
*/
public class RotateArrayTest {
	/**
	* Array [2].
	*/
	@Test
	public void whenRotateTwoRowTwoColArrayThenRotatedArray() {
		RotateArray rotate = new RotateArray();
		int[][] array = {{1, 2}, {3, 4}};
		int[][] resultArray = rotate.rotate(array);
		int[][] expectArray = {{3, 1}, {4, 2}};
		assertThat(resultArray, is(expectArray));
	}
	/**
	* Array [3].
	*/
	@Test
    public void whenRotateThreeRowThreeColArrayThenRotatedArray() {
		RotateArray rotate = new RotateArray();
		int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		int[][] resultArray = rotate.rotate(array);
		int[][] expectArray = {{7, 4, 1}, {8, 5, 2}, {9, 6, 3}};
		assertThat(resultArray, is(expectArray));
	}
}