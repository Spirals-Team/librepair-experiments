package ru.job4j.generic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.*;

public class ConvertlisttoarrTest {
    @Test
    public void convertListconvertTest() {
        Convertlisttoarr convertList = new Convertlisttoarr();
        ArrayList<int[]> list = new ArrayList<>();
        list.add(new int[]{1, 2});
        list.add(new int[]{3, 4, 5, 6});
        ArrayList<Integer> result = convertList.convert(list);
        List<Integer> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));
        assertThat(expected, is(result));
    }
}