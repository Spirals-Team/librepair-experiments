package ru.job4j.threads;

import java.util.List;

/**
 * Run concurrent threads.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class Runner {

    private List<String> regexps;

    public Runner(List<String> regexps) {
        this.regexps = regexps;
    }

    /**
     * Run threads.
     *
     * @param text
     */
    public void runCounts(String text) {
        for (String regex : this.regexps) {
            new Thread(new Counter(text, regex)).start();
        }
    }
}
