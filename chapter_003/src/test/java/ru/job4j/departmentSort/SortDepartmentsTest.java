package ru.job4j.departmentSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SortDepartmentsTest {
    @Test
    public void whenAscendingTest() {
        List<String> result = new ArrayList<>
                (Arrays.asList( "K1\\SK1",
                                "K1\\SK2",
                                "K1\\SK1\\SSK1",
                                "K1\\SK1\\SSK2",
                                "K2",
                                "K2\\SK1\\SSK1",
                                "K2\\SK1\\SSK2"));

        SortDepartments sortDepartments = new SortDepartments(result);
        result = sortDepartments.sort(1);

        ArrayList<String> expected = new ArrayList<>
                (Arrays.asList( "K1",
                                "K1\\SK1",
                                "K1\\SK1\\SSK1",
                                "K1\\SK1\\SSK2",
                                "K1\\SK2",
                                "K2",
                                "K2\\SK1",
                                "K2\\SK1\\SSK1",
                                "K2\\SK1\\SSK2"));
        assertThat(result, is(expected));
    }

    @Test
    public void whenDescendingTest() {
        List<String> result = new ArrayList<>
                (Arrays.asList( "K1\\SK1",
                        "K1\\SK2",
                        "K1\\SK1\\SSK1",
                        "K1\\SK1\\SSK2",
                        "K2",
                        "K2\\SK1\\SSK1",
                        "K2\\SK1\\SSK2"));

        SortDepartments sortDepartments = new SortDepartments(result);
        result = sortDepartments.sort(-1);
        ArrayList<String> expected = new ArrayList<>
                (Arrays.asList( "K2",
                                "K2\\SK1",
                                "K2\\SK1\\SSK2",
                                "K2\\SK1\\SSK1",
                                "K1",
                                "K1\\SK2",
                                "K1\\SK1",
                                "K1\\SK1\\SSK2",
                                "K1\\SK1\\SSK1"));
        assertThat(result, is(expected));
    }
}