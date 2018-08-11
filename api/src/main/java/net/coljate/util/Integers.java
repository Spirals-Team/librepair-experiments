package net.coljate.util;

import java.util.function.IntFunction;

/**
 *
 * @author Ollie
 */
public class Integers {

    public static void requirePositive(final int i, final IntFunction<String> messageSupplier) throws IllegalArgumentException {
        if (i <= 0) {
            throw new IllegalArgumentException(messageSupplier.apply(i));
        }
    }

    public static void requireNonNegative(final int i, final IntFunction<String> messageSupplier) throws IllegalArgumentException {
        if (i < 0) {
            throw new IllegalArgumentException(messageSupplier.apply(i));
        }
    }

}
