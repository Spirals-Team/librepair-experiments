package ru.job4j;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ConvertListTest {
    private ConvertList convertList = new ConvertList();

    @Test
    public void whenConvertArrayInList() {
        int[][] arr = {{1, 7, 9}, {8, 4, 3}, {45}};
        List<Integer> testList = convertList.toList(arr);
        List<Integer> expectList = new ArrayList<>();
        expectList.add(1);
        expectList.add(7);
        expectList.add(9);
        expectList.add(8);
        expectList.add(4);
        expectList.add(3);
        expectList.add(45);
        assertThat(testList, is(expectList));
    }

    @Test
    public void whenConvertListToArray() {
        List<Integer> testList = new ArrayList<>();
        testList.add(1);
        testList.add(7);
        testList.add(9);
        testList.add(8);
        testList.add(4);
        testList.add(3);
        testList.add(45);
        int[][] testArray = convertList.toArray(testList, 3);
        int[][] expectArray = {{1, 7, 9}, {8, 4, 3}, {45, 0, 0}};
        assertThat(testArray, is(expectArray));
    }

    @Test
    public void whenConvertArrayListToList() {
        List<int[]> list = new ArrayList<int[]>();
        list.add(new int[] {77, 99});
        list.add(new int[] {22, 45, 69, 88});
        List<Integer> testList = convertList.convert(list);
        List<Integer> expectList = new ArrayList<Integer>();
        expectList.add(77);
        expectList.add(99);
        expectList.add(22);
        expectList.add(45);
        expectList.add(69);
        expectList.add(88);
        assertThat(testList, is(expectList));
    }
}