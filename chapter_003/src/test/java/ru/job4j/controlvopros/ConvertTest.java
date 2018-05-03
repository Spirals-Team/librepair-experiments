package ru.job4j.controlvopros;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ConvertTest {

    @Test
    public void makeList() {
        int[][] array = new int[][]{
                {1, 2},
                {3, 4}};

        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);

        List<Integer> result = new Convert().makeList(array);
        assertThat(expected, is(result));
    }

    @Test
    public void makeArray() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        int[][] array = new Convert().makeArray(list, 2);
        int[][] expected = new int[][]{
                {1, 2},
                {3, 4}};

        assertThat(expected, is(array));

    }
}
