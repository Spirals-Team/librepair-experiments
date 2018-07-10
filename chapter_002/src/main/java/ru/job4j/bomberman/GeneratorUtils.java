package ru.job4j.bomberman;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Util class to generate entities randomly.
 */
public class GeneratorUtils {

    public static int getRandom(final int min, final int max) {
        return current().nextInt(min, max);
    }

    public static Point randomPoint(final int min, final int max) {
        return new Point(getRandom(min, max), getRandom(min, max));
    }
}
