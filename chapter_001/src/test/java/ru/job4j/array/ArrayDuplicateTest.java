package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ArrayDuplicateTest {
    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate() {
        //напишите здесь тест, проверяющий удаление дубликатов строк из массива строк.
        String[] arg={"Привет", "Мир", "Привет", "Супер", "Мир"};
        String[] res= {"Привет", "Мир", "Супер"};
        ArrayDuplicate ss= new ArrayDuplicate();
        assertThat(ss.remove(arg),arrayContainingInAnyOrder(res));
    }
}
