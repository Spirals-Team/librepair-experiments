package ru.job4j;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class ConvertListTest {
    @Test
    public void whenDoubleArrayThenList() {
        ConvertList convert = new ConvertList();
        List<Integer> list;
        int[][] array = {
                {5, 7, 9},
                {10, 11}
                        };
        list = convert.toList(array);

        assertThat(list.get(0), is(5));
        assertThat(list.get(1), is(7));
        assertThat(list.get(2), is(9));
        assertThat(list.get(3), is(10));
        assertThat(list.get(4), is(11));
    }

    @Test
    public void whenListThenDoubleArray() {
        ConvertList convert = new ConvertList();
        List<Integer> list = new ArrayList<>();
        int[][] current = {
                {5, 7, 9, 10},
                {11, 0, 0, 0}
                          };
        int[][] array;
        list.add(5);
        list.add(7);
        list.add(9);
        list.add(10);
        list.add(11);
        array = convert.toArray(list, 4);

        for (int out = 0; out < 2; out++) {
            for (int in = 0; in < 4; in++) {
                assertThat(array[out][in], is(current[out][in]));
            }
        }
    }
}
