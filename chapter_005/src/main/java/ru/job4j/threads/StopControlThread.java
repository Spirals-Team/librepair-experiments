package ru.job4j.threads;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class StopControlThread {
    public void start(String st, int timeMSec) {
        Thread threadCount = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    int sum = 0;
                    for (int i = 0; i < st.length(); i++) {
                        int index = st.charAt(i);
                        sum = i;
                        if (Thread.interrupted()) {
                            System.out.println(String.format("Stop thread (%d)", i));
                            return;
                        }
                    }
                    System.out.println(String.format("Correct end thread (%d)", sum));
                    return;
                }
            }
        });
        Thread threadTime = new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                long currenTime;
                do {
                    currenTime = System.currentTimeMillis();
                    if (currenTime - startTime > timeMSec) {
                        threadCount.interrupt();
                    }
                } while (currenTime - startTime < timeMSec);
            }
        });
        threadCount.start();
        threadTime.start();
        try {
            threadTime.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StopControlThread stopControlThread = new StopControlThread();
        String st = "Реализовать механизм программнной остановки потока.";
        for (int i = 0; i < 10; i++) {
            st = st.concat(st);
        }
        stopControlThread.start(st, 1);
    }
}
