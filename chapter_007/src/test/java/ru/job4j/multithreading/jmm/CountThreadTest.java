package ru.job4j.multithreading.jmm;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 20.12.17;
 * @version $Id$
 * @since 0.1
 */
public class CountThreadTest {
    @Test
    public void whenIncrementFromManiThreads() {
        Count count = new Count();
        for (int i = 0; i < 200; i++) {
            CountThread countThread = new CountThread(count);
            countThread.start();
            try {
                countThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

            assertThat(count.getCount(), is(200000L));
    }
}