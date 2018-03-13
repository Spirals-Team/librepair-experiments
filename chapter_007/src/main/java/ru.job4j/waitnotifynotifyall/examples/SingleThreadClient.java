package ru.job4j.waitnotifynotifyall.examples;

public class SingleThreadClient {

    public static void main(String[] args) {
        Counter counter = new Counter();

        long start = System.nanoTime();

        double value = 0;
        for (int i = 0; i < 400; i++) {
            value += counter.count(i);

            System.out.println(i);
        }

        System.out.println(("Executed by %d s, value : " + (System.nanoTime() - start) / (1000_000_000) + value));
    }


}

class Counter {

    public Double count(double a) {
        for (int i = 0; i < 1000000; i++) {
            a = a + Math.tan(a);
        }

        return a;
    }
}
