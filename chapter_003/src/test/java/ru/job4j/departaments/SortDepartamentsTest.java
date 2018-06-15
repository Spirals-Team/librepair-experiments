package ru.job4j.departaments;

import org.junit.Test;

import java.util.Arrays;
import java.util.TreeSet;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class SortDepartamentsTest {
   private String[] actual = (new String[]{
            "K1\\SK1",
            "K1\\SK2",
            "K1\\SK1\\SSK1",
            "K1\\SK1\\SSK2",
            "K2",
            "K2\\SK1\\SSK1",
            "K2\\SK1\\SSK2",
            "K2\\SK1"
    });

        @Test
    public void whenSort() {
            SortDepartaments sort = new SortDepartaments();

        TreeSet<String> expected = new TreeSet<>();
        String[] temp = (new String[] {
                "K1",
                "K1\\SK1",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K1\\SK2",
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"
        });
        expected.addAll(Arrays.asList(temp));
            assertThat(sort.sortDepartments(actual), is(expected));
    }

    @Test
    public void whenReverse() {
            SortDepartaments reverse = new SortDepartaments();
        TreeSet expected = new TreeSet();
        String[] temp = new String[]{
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK2",
                "K2\\SK1\\SSK1",
                "K1",
                "K1\\SK2",
                "K1\\SK1",
                "K1\\SK1\\SSK2",
                "K1\\SK1\\SSK1"
        };
        expected.addAll(Arrays.asList(temp));
        assertThat(reverse.reverse(actual), is(expected));
        }
    }