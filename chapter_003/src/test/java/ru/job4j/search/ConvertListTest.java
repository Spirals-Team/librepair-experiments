package ru.job4j.search;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ConvertListTest {
    @Test
    public void whenArrayToList() {
        ConvertList convertList = new ConvertList();
		int[][] array = {{1, 2}, {3, 4}}; 
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		List<Integer> listResult = new ArrayList<>();
		listResult = convertList.toList(array);
        assertThat(listResult, is(list));
    }
	
	@Test
    public void whenListToArray() {
        ConvertList convertList = new ConvertList();
		int[][] arrayResult = {{1, 2}, {3, 4}, {5, 0}}; 
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		int[][] array = convertList.toArray(list, 2);
        assertThat(arrayResult, is(array));
    }
	
	@Test
    public void whenConverArraystoList() {
		List<int[]> list = new ArrayList<>(); 
		list.add(new int[]{1, 2});
		list.add(new int[]{3, 4, 5, 6});
		List<Integer> result = new ConvertList().convert(list);
		List<Integer> convertResult = Arrays.asList(1, 2, 3, 4, 5, 6);
        assertThat(convertResult, is(result));
    }
}