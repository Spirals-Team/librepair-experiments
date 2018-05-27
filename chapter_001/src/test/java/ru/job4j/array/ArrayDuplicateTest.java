package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ArrayDuplicateTest {

    /**
     * Тест удаления дубликатов.
     */
    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate() {
        ArrayDuplicate arrayDuplicate = new ArrayDuplicate();
        String[] testArray = {"Green", "Red", "Red", "Black", "Red", "Green", "Green"};
        testArray = arrayDuplicate.remove(testArray);
        String[] expectArray = {"Green", "Red", "Black"};
        assertThat(testArray, is(expectArray));
    }

}