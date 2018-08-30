package ru.job4j.threads;

import java.util.ArrayList;
import java.util.List;

/**
 * Run concurrent threads and interrupt their.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class InterruptedRunner {

    private List<String> regexps;
    private ArrayList<Thread> names = new ArrayList<>();

    public InterruptedRunner(List<String> regexps) {
        this.regexps = regexps;
    }

    private void runCounts(String text, long timeout) {

        for (String regex : this.regexps) {
            Thread t =
                new Thread(new Time(timeout, new CountChar(text, regex)));
            t.start();
            names.add(t);
        }
    }

    /**
     * Run threads and wait till all of them are finished.
     *
     * @param text
     */
    public void runWithWait(String text, long timeout) {
        System.out.println("start");
        runCounts(text, timeout);
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
