package ru.job4j;

import java.util.Arrays;
import org.junit.Test;
import static org.hamcrest.collection.IsArrayContainingInAnyOrder.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConcatTest {
	@Test
	public void whenMergeArraysThenReturnNewArray() {
		Concat c = new Concat();
		int[] a = {1, 2, 3, 4, 5, 16};
        int[] b = {6, 7, 8, 9, 10, 11, 12};
		int[] result = c.merge(a, b); 
		assertThat(Arrays.toString(result), is("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 16]"));
	}
}