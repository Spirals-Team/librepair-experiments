package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 27.09.17
 * @ version: java_kurs_standart
 * Задание 5.1.1. Итератор для двухмерного массива int[][].
 * Тестовый класс.
 */
public class ArrayIteratorTest {
    /**
     * Проверим доступность элемента.
     * @throws Exception возможно исключение.
     */
    @Test
    public void whenGetNextElement() throws Exception {
        int[][] value = new int[][] {{1, 2}, {3, 4, 5}, {6}};
        ArrayIterator ar = new ArrayIterator(value);

        ar.next();
        ar.next();
        int result = (int) ar.next();

        Assert.assertTrue(result == 3);
    }

    /**
     * Проверим недоступность елемента массива.
     * @throws Exception возможно исключение.
     */
    @Test
    public void whetGetElementFromArray() throws Exception {
        int[][] value = new int[][] {{1}, {2}};
        ArrayIterator ar = new ArrayIterator(value);

        ar.next();
        ar.next();
        ar.hasNext();
        boolean result = ar.hasNext();

        Assert.assertThat(result, is(false));
    }

}