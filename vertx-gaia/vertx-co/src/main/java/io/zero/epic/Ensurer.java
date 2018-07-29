package io.zero.epic;

import io.vertx.zero.exception.heart.ArgumentException;
import io.vertx.zero.log.Errors;

final class Ensurer {

    static void eqLength(
            final Class<?> clazz,
            final int expected,
            final Object... args
    ) {
        if (expected != args.length) {
            final String method = Errors.method(Ensurer.class, "eqLength");
            throw new ArgumentException(clazz, method, expected, "=");
        }
    }

    static void gtLength(
            final Class<?> clazz,
            final int min,
            final Object... args
    ) {
        if (min >= args.length) {
            final String method = Errors.method(Ensurer.class, "gtLength");
            throw new ArgumentException(clazz, method, min, ">");
        }
    }
}
