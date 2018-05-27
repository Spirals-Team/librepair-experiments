package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class TurnTest {

    /**
     * Тест проверяющий переворот массива с чётным числом элементов, например {2, 6, 1, 4}.
     */
    @Test
    public void whenTurnArrayWithEvenAmountOfElementsThenTurnedArray() {
        Turn turn = new Turn();
        int[] rslArray = {2, 6, 1, 4};
        rslArray = turn.back(rslArray);
        int[] expectArray = {4, 1, 6, 2};
        assertThat(rslArray, is(expectArray));
    }

    /**
     * Тест проверяющий переворот массива с нечётным числом элементов, например {1, 2, 3, 4, 5}.
     */
    @Test
    public void whenTurnArrayWithOddAmountOfElementsThenTurnedArray() {
        Turn turn = new Turn();
        int[] rslArray = {1, 2, 3, 4, 5};
        rslArray = turn.back(rslArray);
        int[] expectArray = {5, 4, 3, 2, 1};
        assertThat(rslArray, is(expectArray));
    }
}
