package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 28.09.17
 * @ version: java_kurs_standart
 * 5.1.3. Создать итератор простые числа.
 * Класс тест.
 */
public class SimpleNumberIteratorTest {
    /**
     * Проверим наличие простого числа в массиве.
     */
    @Test
    public void whenSimpleNumberInArray() {
        int[] num = new int[]{1, 4, 13};
        SimpleNumberIterator itr = new SimpleNumberIterator(num);

        boolean test = itr.hasNext();

        Assert.assertTrue(test);
    }

    /**
     * Проверим отсутствие простого числа в массиве.
     */
    @Test
    public void whenSimpleNumberOutArray() {
        int[] num = new int[]{1, 4, 6};
        SimpleNumberIterator itr = new SimpleNumberIterator(num);

        boolean test = itr.hasNext();

        Assert.assertFalse(test);
    }
    /**
     * Найдем простое число в массиве.
     */
    @Test
    public void whenSimpleNumberInArrayWhenReturnNumber() {
        int[] num = new int[]{1, 4, 13};
        SimpleNumberIterator itr = new SimpleNumberIterator(num);

        int result = (int) itr.next();
        int searchNumber = 13;

        Assert.assertThat(result, is(searchNumber));
    }

}