package ru.job4j.departments;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SortTest {
    @Test
    public void sortAscendingTest() {
        ArrayList<String> input = new ArrayList<>();
        input.add("K1\\SK1\\SSK2");
        input.add("K1\\SK1");
        input.add("K1\\SK2");
        input.add("K2\\SK1\\SSK2");
        input.add("K1\\SK1\\SSK1");
        input.add("K2");
        input.add("K2\\SK1\\SSK1");
        Sort sorter = new Sort();
        ArrayList<String> output = sorter.sortAscending(input);
        System.out.println(output);
        assertThat(output.toString(), is("[K1, K1\\SK1, K1\\SK1\\SSK1, K1\\SK1\\SSK2, K1\\SK2, K2, K2\\SK1, K2\\SK1\\SSK1, K2\\SK1\\SSK2]"));
    }

    @Test
    public void whenSortDecrease() {
        ArrayList<String> input = new ArrayList<>();
        input.add("K1\\SK1\\SSK2");
        input.add("K1\\SK1");
        input.add("K1\\SK2");
        input.add("K2\\SK1\\SSK2");
        input.add("K1\\SK1\\SSK1");
        input.add("K2");
        input.add("K2\\SK1\\SSK1");
        Sort sorter = new Sort();
        ArrayList<String> output = sorter.diminution(input);
        System.out.println(output);
        assertThat(output.toString(), is("[K2, K2\\SK1, K2\\SK1\\SSK2, K2\\SK1\\SSK1, K1, K1\\SK2, K1\\SK1, K1\\SK1\\SSK2, K1\\SK1\\SSK1]"));
    }
}