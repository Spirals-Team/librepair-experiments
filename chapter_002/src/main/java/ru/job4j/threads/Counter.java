package ru.job4j.threads;

import java.util.Arrays;
import java.util.List;

/**
 * String, character counter.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class Counter implements Runnable {

    private String text;
    private String regex;

    public Counter(String text, String regex) {
        this.text = text;
        this.regex = regex;
    }

    private void count() {
        int counter = 0;
        List<String> symbols = Arrays.asList(this.text.split(this.regex));
        for (String symbol : symbols) {
            counter++;
            System.out.printf(
                "%s: %d%n", Thread.currentThread().getName(), counter);
        }
        System.out.printf(
            "%s: total = %d%n", Thread.currentThread().getName(), counter);
    }

    @Override
    public void run() {
        count();
    }
}
