package ru.job4j.synchronizy;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = new User(1, 500);
    }

    @Test
    public void createUser() {
        assertThat(user.getId(), is(1));
        assertThat(user.getAmount(), is(500));
    }

    @Test
    public void updateAmount() {
        user.setAmount(600);
        assertThat(user.getAmount(), is(600));
    }
}
