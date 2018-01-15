package com.codahale.metrics;

@Deprecated
public abstract class Clock {

    public abstract long getTick();

    public long getTime() {
        return System.currentTimeMillis();
    }

    public static Clock defaultClock() {
        return UserTimeClockHolder.DEFAULT;
    }

    public static class UserTimeClock extends Clock {

        @Override
        public long getTick() {
            return System.nanoTime();
        }
    }

    private static class UserTimeClockHolder {
        private static final Clock DEFAULT = new UserTimeClock();
    }

    public static class Adapter extends io.dropwizard.metrics5.Clock {

        private final Clock clock;

        public Adapter(Clock clock) {
            this.clock = clock;
        }

        @Override
        public long getTick() {
            return clock.getTick();
        }
    }
}
