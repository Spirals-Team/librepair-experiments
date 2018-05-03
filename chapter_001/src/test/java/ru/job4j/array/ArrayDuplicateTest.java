package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ArrayDuplicateTest {
    @Test
    public void testirovanieArrayDuplicateTEST() {
        String[] array = {"мама", "мыла", "раму", "мама", "мыла", "раму"};
        String[] array2 = new ArrayDuplicate().remove(array);
        String[] expected = {"мама", "мыла", "раму"};
        assertThat(array2, is(expected));
    }
}
