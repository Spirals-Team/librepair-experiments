package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 *Test the method that removes duplicate values from an array.
 *
 *@author Afonin Sergey (mailto:afonin1c@mail.ru)
 *
 *@version 1
 *
 *@since 11.10.2017
 */
public class ArrayDuplicateTest {
    /**
     * Test the method that removes duplicate values from an array.
     */
    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate() {
        //напишите здесь тест, проверяющий удаление дубликатов строк из массива строк.
        String[] testArray =  {"Привет", "Мир", "Привет", "Супер", "Мир"};
        ArrayDuplicate arrayDuplicate = new ArrayDuplicate();
        String[]  result = arrayDuplicate.remove(testArray);
        String[] expected = {"Привет", "Мир", "Супер"};
        assertThat(result, is(expected));
    }
}