package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
* Test.
* @author Igor Fedorenko (mailto:if.zommy@gmail.com)
* @version $Id$
* @since 0.1
*/
public class MergeSortTest {
	/**
	* Merge arrays.
	*/
	@Test
	public void whenFirstSortArrayMergeWithSecondSortArraThenThirdSortArray() {
		MergeSort merge = new MergeSort();
		int[] a1 = {1, 3, 4, 8, 12, 23};
		int[] a2 = {0, 1, 1, 3, 5, 8, 8, 15, 35};
		int[] resultArray = merge.sort(a1, a2);
		int[] expectArray = {0, 1, 1, 1, 3, 3, 4, 5, 8, 8, 8, 12, 15, 23, 35};
		assertThat(resultArray, is(expectArray));
	}
}