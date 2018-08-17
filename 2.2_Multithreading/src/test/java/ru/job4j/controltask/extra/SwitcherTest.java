package ru.job4j.controltask.extra;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SwitcherTest {
    private Switcher sw;
    private int cycle;
    private static final int WORK_A = 0;
    private static final int WORK_B = 1;

    private Thread thrA = new Thread(new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < cycle; i++) {
                try {
                    sw.add(1, WORK_A, WORK_B);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    private Thread thrB = new Thread(new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < cycle; i++) {
                try {
                    sw.add(2, WORK_B, WORK_A);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });


    /**
     * Test №1.
     * @throws InterruptedException exception
     */
    @Test
    public void whenWeAdd60ElementsThenTheLengthWillBe60() throws InterruptedException {
        sw = new Switcher();
        cycle = 3;

        thrA.start();
        thrB.start();
        thrA.join();
        thrB.join();
        int result = 60;
        int expect = sw.length();

        assertThat(result, is(expect));
    }

    /**
     * Test №2.
     * @throws InterruptedException exception
     */
    @Test
    public void whenWeAddElementsThenTheLengthWillBeConsistingRightSequence() throws InterruptedException {
        sw = new Switcher();
        cycle = 2;

        thrA.start();
        thrB.start();
        thrA.join();
        thrB.join();
        String result = "1111111111222222222211111111112222222222";
        String expect = sw.getStr();

        assertThat(result, is(expect));
    }
}