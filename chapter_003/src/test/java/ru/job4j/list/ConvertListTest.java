package ru.job4j.list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConvertListTest {
    @Test
    public void convertTest() {
        ConvertList convertList = new ConvertList();
        List<int[]> list = new ArrayList<>(Arrays.asList(new int[]{1, 2}, new int[]{3, 4, 5, 6, 7}, new int[]{8, 9}));
        List result = convertList.convert(list);
        List<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        assertThat(result, is(expected));
    }
}
