package ru.job4j.convertlist;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * ConvertListTest.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class ConvertListTest {
    /*** When input array then return converted to list.*/
    @Test
    public void whenInputArrayThenReturnConvertedToList() {
        ConvertList convertList = new ConvertList();
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        List<Integer> result = convertList.toList(array);
        List<Integer> expect = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        assertThat(result, is(expect));
    }
    /*** When input list then return converted to array.*/
    @Test
    public void whenInputListThenReturnConvertedToArray() {
        ConvertList convertList = new ConvertList();
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        int[][] result = convertList.toArray(list, 3);
        int[][] expect = {{1, 2, 3}, {4, 5, 6}, {7, 0, 0}};
        assertThat(result, is(expect));
    }
    /*** Input list of array, return list of integers.*/
    @Test
    public void whenListOfArraysThenListOfIntegers() {
        ConvertList convertList = new ConvertList();
        List<int[]> list = new ArrayList<>(Arrays.asList(new int[] {1, 2}, new int[] {3, 4, 5}));
        List<Integer> result = convertList.convert(list);
        List<Integer> expect = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        assertThat(result, is(expect));
    }
}
