package ru.job4j.bomberman;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Possible directions.
 */
public enum Direction {

    LEFT, UP, RIGHT, DOWN;

    private static final List<Direction> DIRECTIONS =
        unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = values().length;

    /**
     * Generate direction randomly
     *
     * @return direction
     */
    public static Direction getRandomDirection() {
        return DIRECTIONS.get(current().nextInt(0, SIZE));
    }
}
