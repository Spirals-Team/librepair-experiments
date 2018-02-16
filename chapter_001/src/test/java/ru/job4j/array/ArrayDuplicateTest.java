package ru.job4j.array;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;

/**
 * ArrayDuplicate.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class ArrayDuplicateTest {
    /**
     * Remove duplicates in array.
     */
    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate() {
        String[] array = {"Привет", "Мир", "Привет", "Супер", "Мир"};
        ArrayDuplicate arrayDuplicate = new ArrayDuplicate();
        String[] resultArray = arrayDuplicate.remove(array);
        String[] expectArray = {"Привет", "Мир", "Супер"};
        assertThat(resultArray, arrayContainingInAnyOrder(expectArray));
    }
}
