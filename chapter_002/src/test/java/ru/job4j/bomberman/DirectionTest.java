package ru.job4j.bomberman;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class DirectionTest {

    @Test
    public void getRandomDirection() {
        Direction direction = Direction.getRandomDirection();
        assertTrue(Arrays.asList(Direction.values()).contains(direction));
    }
}