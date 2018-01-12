package org.corfudb.test.parameters;

import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Duration;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Param {
    TIMEOUT_VERY_SHORT(Duration.of(1, SECONDS), Duration.of(100, MILLIS)),
    TIMEOUT_SHORT(Duration.of(5, SECONDS), Duration.of(1, SECONDS)),
    TIMEOUT_NORMAL(Duration.of(20, SECONDS), Duration.of(10, SECONDS)),
    TIMEOUT_LONG(Duration.of(2, MINUTES), Duration.of(1, MINUTES)),

    NUM_ITERATIONS_VERY_LOW(1, 10),
    NUM_ITERATIONS_LOW(10, 100),
    NUM_ITERATIONS_MODERATE(100, 1000),
    NUM_ITERATIONS_LARGE(1000, 10_000),

    CONCURRENCY_ONE(1, 1),
    CONCURRENCY_TWO(2, 2),
    CONCURRENCY_SOME(3, 5),
    CONCURRENCY_LOTS(25, 100),

    RANDOM_SEED(getSeed(), getSeed())
    ;

    final Object travisValue;
    final Object defaultValue;

    public Object getValue() {
        if (System.getProperty("test.travisBuild") != null
            && System.getProperty("test.travisBuild").toLowerCase().equals("true")) {
            return travisValue;
        }
        return defaultValue;
    }

    private static long getSeed() {
        return System.getProperty("test.seed") == null ? 0L :
            Long.parseLong(System.getProperty("test.seed"));
    }
}
