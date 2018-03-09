package ru.aserdyuchenko.testTaskByQuestion;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by anton on 14.05.17.
 */
public class SortTest {
    @Test
    public void whenSortByChar() {
        Sort sort = new Sort();
        List<String> list = Arrays.asList("a a", "a   a", "a", "a a a");
        List<String> result = sort.sort(list, ' ');
        assertThat(result.toString(), is("[a, a a, a a a, a   a]"));
    }

    @Test
    public void whenSortByHigh() {
        Sort sort = new Sort();
        List<String> list = Arrays.asList("K1/SK1", "K1/SK2", "K1/SK1/SSK1", "K1/SK1/SSK2", "K2", "K2/SK1/SSK1", "K2/SK1/SSK2");
        List<String> newList = sort.addStrings(list);
        List<String> result = sort.sortByHigh(newList);
        assertThat(result.toString(), is("[K1, K1/SK1, K1/SK1/SSK1, K1/SK1/SSK2, K1/SK2, K2, K2/SK1, K2/SK1/SSK1, K2/SK1/SSK2]"));
    }

    @Test
    public void whenSortByLow() {
        Sort sort = new Sort();
        List<String> list = Arrays.asList("K1/SK1", "K1/SK2", "K1/SK1/SSK1", "K1/SK1/SSK2", "K2", "K2/SK1/SSK1", "K2/SK1/SSK2");
        List<String> newList = sort.addStrings(list);
        List<String> result = sort.sortByLow(newList);
        assertThat(result.toString(), is("[K2, K2/SK1, K2/SK1/SSK2, K2/SK1/SSK1, K1, K1/SK2, K1/SK1, K1/SK1/SSK2, K1/SK1/SSK1]"));
    }
}