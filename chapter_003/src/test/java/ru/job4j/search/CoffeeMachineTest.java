package ru.job4j.search;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CoffeeMachineTest {
    @Test
    public void whenGetChange() {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
		coffeeMachine.changes(50, 35);
        int[] result = coffeeMachine.changes(50, 35);
		int[] answer = {10, 5};
        assertThat(result, is(answer));
    }
}