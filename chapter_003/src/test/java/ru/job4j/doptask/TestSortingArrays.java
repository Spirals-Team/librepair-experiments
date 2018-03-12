package ru.job4j.doptask;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Класс тестирования SortingArrays.
 */
public class TestSortingArrays {
    /**
     * Проверяем сортировку массивов по возрастанию.
     */
    @Test
    public void testSortingTop() {
        int[] a = new int[]{1, 2, 1};
        int[] b = new int[]{1, 2, 3};
        int[] c = new int[]{1, 1};
        SortingArrays sa = new SortingArrays();
        List<int[]> department = new ArrayList<>();
        department.addAll(Arrays.asList(a, b, c));
        List<int[]> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(c, a, b));
        assertThat(sa.sortingTop(department), is(expected));
    }

    /**
     * Проверяем сортировку массивов по убыванию.
     */
    @Test
    public void testSortingDown() {
        int[] a = new int[]{1, 2, 1};
        int[] b = new int[]{1, 2, 3};
        int[] c = new int[]{1, 1};
        SortingArrays sa = new SortingArrays();
        List<int[]> department = new ArrayList<>();
        department.addAll(Arrays.asList(a, b, c));
        List<int[]> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(b, a, c));
        assertThat(sa.sortingDown(department), is(expected));
    }
}
