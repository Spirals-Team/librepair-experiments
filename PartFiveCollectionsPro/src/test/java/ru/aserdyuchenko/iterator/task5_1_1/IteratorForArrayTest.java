package ru.aserdyuchenko.iterator.task5_1_1;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author anton.
 */
public class IteratorForArrayTest {
    /**
     * @param itr - создание итератора.
     */
    private IteratorForArray itr = new IteratorForArray(new int[][] {{1, 2}, {3, 4}});

    /**
     * Если трижды вызвать метод next(), то результат будет 3.
     */
    @Test
    public void whenGetNextCallShouldPointerForward() {
        itr.next();
        itr.next();
        int result = itr.next();

        assertThat(result, is(3));
    }

    /**
     * Если элементы в первом одномерном массиве закончатся, то на метод hasNext()
     * возвращается true, так как в следующем одномерном массиве, есть элементы.
     */
    @Test
    public void whenCheckNextPositionShouldReturnTrueForThirdPosition() {
        itr.next();
        itr.next();
        boolean result = itr.hasNext();

        assertThat(result, is(true));
    }

    /**
     * Если элементы в массиве закончатся, то метод hasNext() возвращает false.
     */
    @Test
    public void whenCheckNextPositionShouldReturnFalseForFivePosition() {
        for (int i = 0; i < 4; i++) {
            itr.next();
        }
        boolean result = itr.hasNext();

        assertThat(result, is(false));
    }
}