package ru.aserdyuchenko.map.task_9;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author anton.
 */
public class UserTest {
    private User firstUser = new User("Anton");
    private User secondUser = new User("Anton");
    private User thirdUser = new User("Ivan");

    @Test
    public void whenTwoEqualsObjects() {
        boolean result = firstUser.equals(secondUser);

        assertThat(result, is(true));
    }

    @Test
    public void whenTwoObjextsNotEquals() {
        boolean result = firstUser.equals(thirdUser);

        assertThat(result, is(false));
    }

    @Test
    public void whenOneObjectEqualsHimSelfe() {
        boolean result = firstUser.equals(firstUser);

        assertThat(result, is(true));
    }

}