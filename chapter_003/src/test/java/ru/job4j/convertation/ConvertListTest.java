package ru.job4j.convertation;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConvertListTest {
    @Test
    public void toListTest() {
        ConvertList convertList = new ConvertList();
        int length = 3;
        int[][] array = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                array[i][j] = i * j;
            }
        }
        ArrayList<Integer> list = convertList.toList(array);

        ArrayList<Integer> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(0, 0, 0, 0, 1, 2, 0, 2, 4));
        assertThat(list, is(expected));
    }

    @Test
    public void toArrayTest() {
        ConvertList convertList = new ConvertList();
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.addAll(Arrays.asList(0, 0, 0, 0, 0, 1, 2, 3, 0, 2, 4, 6, 0, 3, 6, 9));

        int length = 4;
        int[][] expected = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                expected[i][j] = i * j;
            }
        }
        int[][] array = convertList.toArray(list, 4);

        assertThat(array, is(expected));
    }

    @Test
    public void convertTest() {
        ConvertList convertList = new ConvertList();
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{1, 2});
        list.add(new int[]{3, 4, 5, 6});
        List<Integer> result = convertList.convert(list);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));
        assertThat(result, is(expected));
    }
}