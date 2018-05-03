package ru.job4j.startcollection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ConvertListTest {
    @Test
    public void masstoListTestong() {
        ConvertList list = new ConvertList();
        int[][] array = new int[][]{
                {1, 2},
                {3, 4}};

        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);

        List<Integer> result = new ArrayList<>();
        result.addAll(list.toList(array));
        assertThat(expected, is(result));
    }

    @Test
    public void listtoMasstest() {
        ConvertList listtoMass = new ConvertList();


        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        int[][] array = listtoMass.toArray(list, 2);
        int[][] expected = new int[][]{
                {1, 2},
                {3, 4}};

        assertThat(expected, is(array));

    }
}