package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *Test reverse array.
 *
 *@author Afonin Sergey (mailto:afonin1c@mail.ru)
 *
 *@version 1
 *
 *@since 10.10.2017
 */
public class TurnTest {
    /**
     * Test reverse array with an even number of elements.
     */
    @Test
    public void whenTurnArrayWithEvenAmountOfElementsThenTurnedArray() {
        //напишите здесь тест, проверяющий переворот массива с чётным числом элементов, например {2, 6, 1, 4}.
        int[] testArray = {2, 6, 1, 4};
        Turn turn = new Turn();
        int[]  result = turn.back(testArray);
        int[] expected = {4, 1, 6, 2};
        assertThat(result, is(expected));
    }

    /**
     * Test reverse array with an odd number of elements.
     */
    @Test
    public void whenTurnArrayWithOddAmountOfElementsThenTurnedArray() {
        //напишите здесь тест, проверяющий переворот массива с нечётным числом элементов, например {1, 2, 3, 4, 5}.
        int[] testArray = {1, 2, 3, 4, 5};
        Turn turn = new Turn();
        int[]  result = turn.back(testArray);
        int[] expected = {5, 4, 3, 2, 1};
        assertThat(result, is(expected));
    }
}