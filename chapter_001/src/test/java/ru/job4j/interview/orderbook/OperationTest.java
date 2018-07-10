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
public class OperationTest {

    @Test(expected = IllegalArgumentException.class)
    public void existOperationFromOperationTitle() {
        assertThat(Operation.operationFromTitle("BUY"), is(Operation.BUY));
        Operation.operationFromTitle("nonExist");
    }
}