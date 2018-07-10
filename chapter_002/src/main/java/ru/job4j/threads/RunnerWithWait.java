package ru.job4j.threads;

import java.util.ArrayList;
import java.util.List;

/**
 * Run concurrent threads and wait while all of them are finished.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class RunnerWithWait {

    private List<String> regexps;
    private ArrayList<Thread> names = new ArrayList<>();

    public RunnerWithWait(List<String> regexps) {
        this.regexps = regexps;
    }

    private void runCounts(String text) {
        for (String regex : this.regexps) {
            Thread t = new Thread(new Counter(text, regex));
            names.add(t);
            t.start();
        }

    }

    /**
     * Run threads and wait till all of them are finished.
     *
     * @param text
     */
    public void runWithWait(String text) {
        System.out.println("start");
        runCounts(text);
        for (Thread name : names) {
            try {
                name.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("finish");
    }
}
