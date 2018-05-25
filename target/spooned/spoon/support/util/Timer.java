package spoon.support.util;


public class Timer {
    private static java.util.List<spoon.support.util.Timer> timestamps = new java.util.ArrayList<>();

    private static java.util.Deque<spoon.support.util.Timer> current = new java.util.ArrayDeque<>();

    public static void start(java.lang.String name) {
        spoon.support.util.Timer.current.push(new spoon.support.util.Timer(name));
    }

    public static void stop(java.lang.String name) {
        if (!(spoon.support.util.Timer.current.peek().getName().equals(name))) {
            throw new java.lang.RuntimeException("Must stop last timer");
        }
        spoon.support.util.Timer.current.peek().stop();
        spoon.support.util.Timer.timestamps.add(spoon.support.util.Timer.current.pop());
    }

    public static void display() {
        for (spoon.support.util.Timer time : spoon.support.util.Timer.timestamps) {
            java.lang.System.out.println(time);
        }
    }

    java.lang.String name;

    long start;

    long stop;

    public Timer(java.lang.String name) {
        super();
        this.name = name;
        start = java.lang.System.currentTimeMillis();
    }

    public void stop() {
        stop = java.lang.System.currentTimeMillis();
    }

    public java.lang.String getName() {
        return name;
    }

    public long getValue() {
        return (stop) - (start);
    }

    @java.lang.Override
    public java.lang.String toString() {
        return (((getName()) + " \t") + (getValue())) + "ms";
    }
}

