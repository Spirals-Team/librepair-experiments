package ru.aserdyuchenko.map.task_9_1;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author anton.
 */
public class UserTest {
    private User first = new User("Anton");
    private User second = new User("Anton");

    @Test
    public void whenTwoObjectsEqualsTheyHaveTheSameHashCode() {
        boolean equalsResult = first.equals(second);
        boolean hashCodeResult = first.hashCode() == second.hashCode();

        assertThat(equalsResult, is(hashCodeResult));
    }
}