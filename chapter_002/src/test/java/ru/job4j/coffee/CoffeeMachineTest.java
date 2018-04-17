package ru.job4j.coffee;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class CoffeeMachineTest {
    @Test
    public void whenGiveMoneyThenGetRaisingLikeArray() {
        CoffeeMachine machine = new CoffeeMachine();
        int[] cur = machine.changes(45, 17);
        int[] result = {10, 10, 5, 2, 1};

        for (int i = 0; i < 4; i++) {
            assertThat(cur[i], is(result[i]));
        }
    }
}
