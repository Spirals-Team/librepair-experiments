package ru.job4j.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test ConvertListTest.
 * @author Igor Fedorenko (mailto:if.zommy@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ConvertListTest {
    /**
	*Test from Array to List.
	*/
	@Test
	public void whenAddArrayThenReturnList() {
		ConvertList cl = new ConvertList();
		int[][] arr = {{1, 2, 3}, {11, 12, 13}, {21, 22, 23}};
		List<Integer> expect = Arrays.asList(1, 2, 3, 11, 12, 13, 21, 22, 23);
		List<Integer> result = cl.toList(arr);
		assertThat(result, is(expect));
	}
	/**
	*Test from List to Array.
	*/
   @Test
	public void whenAddListAndValueOfRowThenReturnArray() {
	   ConvertList cl = new ConvertList();
	   List<Integer> list = Arrays.asList(1, 2, null, 4, 5, 6, 7, 8, 9, 10);
	   int[][] result = cl.toArray(list, 4);
	   int[][] expect = {{1, 2, 0}, {4, 5, 6}, {7, 8, 9}, {10, 0, 0}};
	   assertThat(result, is(expect));
	}
	/**
	 *Test from List<int[]> to List<Integer>.
	 */
	@Test
	public void whenAddListOfArraysWhenReturnListOfInteger() {
		ConvertList cl = new ConvertList();
		List<int[]> list = new ArrayList<>();
		list.add(new int[]{1, 2, 7});
		list.add(new int[]{3, 4, 8, 9});
		List<Integer> result = cl.convert(list);
		List<Integer> expect = Arrays.asList(1, 2, 7, 3, 4, 8, 9);
		assertThat(result, is(expect));
	}
}
