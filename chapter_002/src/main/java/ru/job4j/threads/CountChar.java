package ru.job4j.threads;

import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.currentThread;

/**
 * String, character counter.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class CountChar implements Runnable {

    private String text;
    private String regex;

    public CountChar(String text, String regex) {
        this.text = text;
        this.regex = regex;
    }

    private void count() {
        int counter = 0;
        List<String> symbols = Arrays.asList(this.text.split(this.regex));
        for (String symbol : symbols) {
            counter++;
            System.out.printf(
                "CountChar: %s: %d%n", currentThread().getName(), counter);
            if (currentThread().isInterrupted()) {
                System.out.printf(
                    "CountChar %s was interrupted.%n", currentThread().getName());
                break;
            }
        }
        if (!currentThread().isInterrupted()) {
            System.out.printf(
                "CountChar %s: total = %d%n", currentThread().getName(), counter);
        }
    }

    @Override
    public void run() {
        count();
    }
}
