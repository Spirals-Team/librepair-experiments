package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ArrayDuplicateTest {
    @Test
    public void firstArray() {
        ArrayDuplicate arrayDuplicate = new ArrayDuplicate();
        String[] result =  {"Привет", "Мир", "Привет", "Супер", "Мир"};
        String[] expected = {"Привет", "Мир", "Супер"};
        result = arrayDuplicate.remove(result);
        assertThat(result, is(expected));
    }

    @Test
    public void secondArray() {
        ArrayDuplicate arrayDuplicate = new ArrayDuplicate();
        String[] result =  {"Привет", "Привет"};
        String[] expected = {"Привет"};
        result = arrayDuplicate.remove(result);
        assertThat(result, is(expected));
    }
}
