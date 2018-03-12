package ru.job4j.collection;

import org.junit.Test;
import ru.job4j.collection.ConvertList;

import static org.hamcrest.core.Is.is;

import java.util.*;

import static org.junit.Assert.assertThat;

/**
 * Тестовый класс для ConvertList.
 */
public class TestConvertList {
    /**
     * whenConvertListThenGetArray.
     * Из двумерного массива в лист.
     */
    @Test
    public void whenConvertListThenGetArray() {
        int[][] mas = {{7, 6, 5}, {4, 3, 2}, {1, 0, 0}};
        List<Integer> expectedList = new ArrayList<>();
        Collections.addAll(expectedList, 7, 6, 5, 4, 3, 2, 1, 0, 0);
        assertThat(new ConvertList().toList(mas), is(expectedList));
    }

    /**
     * whenConvertArrayThenGetList.
     * Из листа в двумерный массив.
     */
    @Test
    public void whenConvertArrayThenGetList() {
        int[][] expectedMas = {{7, 6, 5}, {4, 3, 2}, {1, 0, 0}};
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, 7, 6, 5, 4, 3, 2, 1);
        assertThat(new ConvertList().toArray(list, 3), is(expectedMas));
    }
    /**
     * whenConvertArrayThenGetListNull.
     * Из листа в двумерный массив c null.
     */
    @Test
    public void whenConvertArrayThenGetListNull() {
        int[][] expectedMas = {{1, 2, 3}, {0, 4, 5}, {6, 0, 0}};
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, 1, 2, 3, null, 4, 5, 6);
        assertThat(new ConvertList().toArray(list, 3), is(expectedMas));
    }

    /**
     * Проверим конвертацию и списка массивов в обычный список.
     */
    @Test
    public void convertListArrayToList() {
        List<int[]> listArray = new ArrayList<>();
        listArray.add(new int[]{1, 2});
        listArray.add(new int[]{3, 4, 5, 6});
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, 1, 2, 3, 4, 5, 6);
        assertThat(new ConvertList().convert(listArray), is(list));
    }
    /**
     * Проверим конвертацию и списка массивов с null в обычный список.
     */
    @Test
    public void convertListArrayToListIsNull() {
        List<int[]> listArray = new ArrayList<>();
        listArray.add(new int[]{1, 2});
        listArray.add(new int[]{3, 4, 5, 6});
        listArray.add(null);
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, 1, 2, 3, 4, 5, 6);
        assertThat(new ConvertList().convert(listArray), is(list));
    }
}
