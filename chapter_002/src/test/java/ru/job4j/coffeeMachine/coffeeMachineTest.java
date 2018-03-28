package ru.job4j.coffeeMachine;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class coffeeMachineTest {
    @Test
    public void ifValueIsFiftyThenChangesIsFifteenTest() {
        coffeeMachine coffeeMachine = new coffeeMachine();
        int[] result = coffeeMachine.changes(50, 35);
        assertThat(result, is(new int[]{5, 10}));
    }

    @Test
    public void ifValueIsFortySevenThenChangesIsNineTest() {
        coffeeMachine coffeeMachine = new coffeeMachine();
        int[] result = coffeeMachine.changes(47, 38);
        assertThat(result, is(new int[]{1, 2, 2, 2, 2}));
    }

    @Test
    public void ifValueIsFortySevenThenChangesIsEighteenTest() {
        coffeeMachine coffeeMachine = new coffeeMachine();
        int[] result = coffeeMachine.changes(47, 30);
        assertThat(result, is(new int[]{1, 2, 2, 2, 10}));
    }
}
