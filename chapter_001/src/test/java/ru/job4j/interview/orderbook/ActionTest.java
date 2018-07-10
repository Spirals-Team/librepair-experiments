package ru.job4j.interview.orderbook;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class ActionTest {

    @Test(expected = IllegalArgumentException.class)
    public void existActionFromActionName() {
        assertThat(Action.actionFromName("AddOrder"), is(Action.ADD));
        Action.actionFromName("nonExist");
    }
}