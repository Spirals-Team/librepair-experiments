package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * ArrayDuplicateTest.
 * @author Hincu Andrei (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ArrayDuplicateTest {
    /**
     *Test.
     */
    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate() {
        String[] array = {"Привет", "Мир", "Привет", "Супер", "Мир"};
        String[] ex = {"Привет", "Мир", "Супер"};
        ArrayDuplicate duplicate = new ArrayDuplicate();
        String[] arrayTest = duplicate.remove(array);
        assertThat(arrayTest, arrayContainingInAnyOrder(ex));
    }
    @Test
    public void test() {
        ArrayDuplicate duplicate = new ArrayDuplicate();
        int[] a = {1, 2, 3};
        int[] b = {4, 5, 6, 7};
        int[] c = duplicate.add(b, a);
        assertThat(c, is(new int[] {1, 2, 3, 4, 5, 6, 7}));
    }
}
