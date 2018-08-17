package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
* Test Bubble sort.
* @author Igor Fedorenko (mailto:if.zommy@gmail.com)
* @version $Id$
* @since 0.1
*/

public class BubbleSortTest {
	/**
	* Array [10].
	*/
	@Test
	public void whenSortArrayWithTenElementsThenSortedArray() {
        BubbleSort bubble = new BubbleSort();
		int[] array = {1, 5, 4, 2, 3, 1, 7, 8, 0, 5};
		int[] resultArray = bubble.sort(array);
		int[] expectArray = {0, 1, 1, 2, 3, 4, 5, 5, 7, 8};
		assertThat(resultArray, is(expectArray));
	}
}