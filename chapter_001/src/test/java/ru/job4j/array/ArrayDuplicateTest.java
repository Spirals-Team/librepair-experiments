package ru.job4j.array;


import org.junit.Test;

import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;
import static org.junit.Assert.assertThat;

/**
 * Test class ArrayDuplicateTest.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 18.09.2017
 */
public class ArrayDuplicateTest {
    /**
     * Method whenSortArrayWithTenElementsThenSortedArray.
     */
    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate() {
        ArrayDuplicate arrayDuplicate = new ArrayDuplicate();
        String[] resultArray = {"Привет", "Мир", "Привет", "Супер", "Мир"};
        String[] expectArray = {"Привет", "Мир", "Супер"};
        assertThat(arrayDuplicate.remove(resultArray), arrayContainingInAnyOrder(expectArray));
    }
}
