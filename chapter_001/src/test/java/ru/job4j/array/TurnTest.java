package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class TurnTest {
    @Test
    public void whenCountableArrayThenInvertArray() {
        Turn turn = new Turn();
        int[] array = {1, 2, 3, 4};
        int[] invertArray = {4, 3, 2, 1};
        assertThat(turn.back(array), is(invertArray));
    }

    @Test
    public void whenUncountableArrayThenInvertArray() {
        Turn turn = new Turn();
        int[] array = {1, 2, 3, 4, 5};
        int[] invertArray = {5, 4, 3, 2, 1};
        assertThat(turn.back(array), is(invertArray));
    }
}