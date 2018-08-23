package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
* Test.
* @author Igor Fedorenko (mailto:if.zommy@gmail.com)
* @version $Id$
* @since 0.1
*/
public class TurnTest {
	/**
	* Array [5].
	*/
	@Test
     public void whenTurnArrayWithOddAmountOfElementsThenTurnedArray() {
        Turn turn = new Turn();
		int[] array = {1, 2, 3, 4, 5};
        int[] resultArray = turn.back(array);
        int[] expectArray = {5, 4, 3, 2, 1};
        assertThat(resultArray, is(expectArray));
	}
	/**
	* Array [6].
	*/
	@Test
    public void whenTurnArrayWithEvenAmountOfElementsThenTurnedArray() {
        Turn turn = new Turn();
		int[] array = {1, 2, 3, 4, 5, 6};
        int[] resultArray = turn.back(array);
        int[] expectArray = {6, 5, 4, 3, 2, 1};
        assertThat(resultArray, is(expectArray));
	}
}