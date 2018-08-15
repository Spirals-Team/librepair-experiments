package ru.job4j.personaltasks;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class SortDepartmentTest.
 */
public class SortDepartmentTest {
    /**
     * Sort Ascending.
     */
    @Test
    public void whenSortByAscendingThenStartK1() {
        SortDepartment sd = new SortDepartment();
        String[] department = {"K1/SK1", "K1/SK2", "K1/SK1/SSK1", "K1/SK1/SSK2", "K2", "K2/SK1/SSK1", "K2/SK1/SSK2"};
        String[] result = sd.sortAscending(department);
        String[] exist = {"K1", "K1/SK1", "K1/SK1/SSK1", "K1/SK1/SSK2", "K1/SK2", "K2", "K2/SK1", "K2/SK1/SSK1", "K2/SK1/SSK2"};
        assertThat(result, is(exist));
    }

    /**
     * Sort Descending.
     */
    @Test
    public void whenSortByDescendingThenStartK2() {
        SortDepartment sd = new SortDepartment();
        String[] department = {"K1/SK1", "K1/SK2", "K1/SK1/SSK1", "K1/SK1/SSK2", "K2", "K2/SK1/SSK1", "K2/SK1/SSK2"};
        String[] result = sd.sortDescending(department);
        String[] exist = {"K2", "K2/SK1", "K2/SK1/SSK2", "K2/SK1/SSK1", "K1", "K1/SK2", "K1/SK1", "K1/SK1/SSK2", "K1/SK1/SSK1"};
        assertThat(result, is(exist));
    }
}
