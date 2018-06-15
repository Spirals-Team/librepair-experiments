package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;

public class ArrayDuplicateTest {
    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate() {
        String[] input = {"Привет", "Мир", "Привет", "Супер", "Мир"};
        String[] expect = {"Привет", "Мир", "Супер"};
        ArrayDuplicate d = new ArrayDuplicate();
        String[] result = d.remove(input);
        assertThat(result, arrayContainingInAnyOrder(expect));

    }
}