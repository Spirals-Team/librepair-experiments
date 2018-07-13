package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TurnTest {
    @Test
    public void whenTurnArrayWithEvenAmountOfElementsThenTurnedArray() {
        //напишите здесь тест, проверяющий переворот массива с чётным числом элементов, например {2, 6, 1, 4}
        Turn t1= new Turn();
        int[] a={2,6,1,4};
        int[] res={4,1,6,2};
        assertThat(t1.back(a),is(res));

    }

    @Test
    public void whenTurnArrayWithOddAmountOfElementsThenTurnedArray() {
        //напишите здесь тест, проверяющий переворот массива с нечётным числом элементов, например {1, 2, 3, 4, 5}.
        Turn t1= new Turn();
        int[] a={1,2,3,4,5};
        int[] res={5,4,3,2,1};
        assertThat(t1.back(a),is(res));

    }
}