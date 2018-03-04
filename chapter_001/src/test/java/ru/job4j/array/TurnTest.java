package ru.job4j.array;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * Paint.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class TurnTest {
    /**
     * Array with even amount elements.
     */
    @Test
    public void whenTurnArrayWithEvenAmountOfElementsThenTurnedArray() {
       Turn turn = new Turn();
       int[] resultArray = turn.back(new int[] {1, 2, 3, 4});
       assertThat(resultArray, is(new int[]{4, 3, 2, 1}));
    }
    /**
     * Array with odd amount elements.
     */
    @Test
    public void whenTurnArrayWithOddAmountOfElementsThenTurnedArray() {
        Turn turn = new Turn();
        int[] resultArray = turn.back(new int[] {1, 2, 3, 4, 5});
        assertThat(resultArray, is(new int[]{5, 4, 3, 2, 1}));
    }
}
