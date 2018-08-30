package ru.job4j.interview.orderbook;

import org.junit.Test;

import java.io.File;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class OrderParserTest {

    @Test
    public void start() {
        new App().run(new File("/home/romul/Downloads/orders.xml"));
    }
}