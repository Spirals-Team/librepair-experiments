package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.collection.IsArrayContainingInAnyOrder.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ArrayDuplicateTest {
    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate() {
        //напишите здесь тест, проверяющий удаление дубликатов строк из массива строк.
		ArrayDuplicate duplicate = new ArrayDuplicate();
		String[] array =  {"Привет", "Мир", "Привет", "Супер", "Мир"}; 
		String[] res = duplicate.remove(array);
		String[] expected = {"Привет", "Мир", "Супер"};
		assertThat(res, arrayContainingInAnyOrder(expected));
    }
}