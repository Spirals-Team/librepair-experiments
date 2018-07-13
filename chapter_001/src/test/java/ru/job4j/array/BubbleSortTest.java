package ru.job4j.array;

import org.junit.Test;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BubbleSortTest {
    @Test
    public void whenSortArrayWithTenElementsThenSortedArray() {
        //напишите здесь тест, проверяющий сортировку массива из 10 элементов методом пузырька, например {1, 5, 4, 2, 3, 1, 7, 8, 0, 5}.
    int[] arg={1, 5, 4, 2, 3, 1, 7, 8, 0, 5};
    int[] result= Arrays.copyOf(arg,arg.length);
    Arrays.sort(result);
    BubbleSort ss=new BubbleSort();
    assertThat(ss.sort(arg),is(result));
    }
}