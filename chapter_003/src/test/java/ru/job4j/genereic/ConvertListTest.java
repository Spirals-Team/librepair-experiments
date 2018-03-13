package ru.job4j.genereic;

import org.junit.Test;
import ru.job4j.generic.ConvertList;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class ConvertListTest {

    @Test
    public void whenConvertOtArrayThenGetArray() {

        List<Integer> list = new ArrayList<Integer>();

//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//        list.add(5);
//        list.add(6);
//        list.add(7);
          list.add(1);
          list.add(2);
          list.add(null);
          list.add(4);
          list.add(5);

        ConvertList convertList = new ConvertList();

//        int[][] result = convertList.toArray(list, 3);
        int[][] result = convertList.toArray(list, 2);

//        int[][] expected = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 0}};
        int[][] expected = new int[][]{{1, 2, 0}, {4, 5, 0}};

        assertThat(result, is(expected));
    }

    @Test
    public void whenConvertToListThenGetList() {

        int[][] array = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 0}};

        ConvertList convertList = new ConvertList();

        List<Integer> result = convertList.toList(array);

        List<Integer> expected = new ArrayList<Integer>();

        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);
        expected.add(6);
        expected.add(7);
        expected.add(0);
        expected.add(0);

        assertThat(result, is(expected));
    }

    @Test
    public void whenConvertListOfArraysThenGetList() {

        List<int[]> list = new ArrayList<int[]>();

        list.add(new int[]{1, 2});
        list.add(new int[]{3, 4, 5, 6});

        List<Integer> result = new ConvertList().convert(list);

        List<Integer> expected = new ArrayList<Integer>();

        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);
        expected.add(6);

        assertThat(result, is(expected));
    }
}