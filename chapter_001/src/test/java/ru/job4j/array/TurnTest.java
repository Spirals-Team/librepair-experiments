package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test class TurnTest.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 14.09.2017
 */

public class TurnTest {
    /**
     * Method whenTurnArrayWithEvenAmountOfElementsThenTurnedArray.
     */
    @Test
    public void whenTurnArrayWithEvenAmountOfElementsThenTurnedArray() {
        //напишите здесь тест, проверяющий переворот массива с чётным числом элементов, например {2, 6, 1, 4}.
        Turn turn = new Turn();
        int[] array = {2, 6, 1, 4};
        int[] resault = {4, 1, 6, 2};
        assertThat(turn.back(array), is(resault));
    }

    /**
     * Method whenTurnArrayWithOddAmountOfElementsThenTurnedArray.
     */
    @Test
    public void whenTurnArrayWithOddAmountOfElementsThenTurnedArray() {
        //напишите здесь тест, проверяющий переворот массива с нечётным числом элементов, например {1, 2, 3, 4, 5}.
        Turn turn = new Turn();
        int[] array = {1, 2, 3, 4, 5};
        int[] resault = {5, 4, 3, 2, 1};
        assertThat(turn.back(array), is(resault));
    }
}
