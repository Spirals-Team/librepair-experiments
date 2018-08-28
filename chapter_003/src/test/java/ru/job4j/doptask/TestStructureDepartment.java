package ru.job4j.doptask;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Класс тестирования StructureDepartment.
 */
public class TestStructureDepartment {
    /**
     * Проверяем сортировку справочника по возрастанию.
     */
    @Test
    public void testSortingTop() {
        StructureDepartment sd = new StructureDepartment();
        List<String> department = new ArrayList<>();
        department.addAll(Arrays.asList("K1\\SK1\\SSK1", "K1\\SK1\\SSK2", "K1\\SK2", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"));
        List<String> expected = new ArrayList<>();
        expected.addAll(Arrays.asList("K1", "K1\\SK1", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2",
                "K1\\SK2", "K2", "K2\\SK1", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"));
        assertThat(sd.sortingTop(department), is(expected));
    }

    /**
     * Проверяем сортировку справочника по убыванию.
     */
    @Test
    public void testSortingDown() {
        StructureDepartment sd = new StructureDepartment();
        List<String> department = new ArrayList<>();
        department.addAll(Arrays.asList("K1\\SK1\\SSK1", "K1\\SK1\\SSK2", "K1\\SK2", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"));
        List<String> expected = new ArrayList<>();
        expected.addAll(Arrays.asList("K2", "K2\\SK1", "K2\\SK1\\SSK2", "K2\\SK1\\SSK1",
                "K1", "K1\\SK2", "K1\\SK1", "K1\\SK1\\SSK2", "K1\\SK1\\SSK1"));
        assertThat(sd.sortingDown(department), is(expected));
    }
}
