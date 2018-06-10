package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TurnTest {
    @Test
    public void whenTurnArrayWithEvenAmountOfElementsThenTurnedArray() {
        //напишите здесь тест, проверяющий переворот массива с чётным числом элементов, например {2, 6, 1, 4}.
		Turn turn = new Turn();
		int[] res = new int[] {2, 6, 1, 4};
		int[] result = turn.back(res);
		int[] expected = new int[] {4, 1, 6, 2};
		assertThat(result, is(expected));
	}

    @Test
    public void whenTurnArrayWithOddAmountOfElementsThenTurnedArray() {
        //напишите здесь тест, проверяющий переворот массива с нечётным числом элементов, например {1, 2, 3, 4, 5}.
		Turn turn = new Turn();
		int[] res = new int[] {1, 2, 3, 4, 5};
		int[] result = turn.back(res);
		int[] expected = new int[] {5, 4, 3, 2, 1};
		assertThat(result, is(expected));
    }
}