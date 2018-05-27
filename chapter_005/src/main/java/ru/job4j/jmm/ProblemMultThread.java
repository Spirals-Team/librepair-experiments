package ru.job4j.jmm;

public class ProblemMultThread {
    private static int count = 0;

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                count++;
                System.out.println(String.format("Thread one, count = %d", count));
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                count++;
                System.out.println(String.format("Thread Two, count = %d", count));
            }
        }.start();
    }
}
