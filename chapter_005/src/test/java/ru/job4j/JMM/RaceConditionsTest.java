package ru.job4j.JMM;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class RaceConditionsTest {
    @Test
    public void whenMethodRunIsSyncShouldBeTrue() throws InterruptedException {
        SynchronizedCounter counter = new SynchronizedCounter();
        Thread one = new Thread(counter);
        Thread two = new Thread(counter);

        one.start();
        two.start();
        one.join();
        two.join();

        assertThat(counter.getCount(), is(2000000));
    }


    /**
     * Not always false, because class Counter is not Thread Safe.
     */
    @Test
    public void whenMethodRunIsNotSyncShouldBeFalse() throws InterruptedException {
//        Counter counter = new Counter();
//        Thread one = new Thread(counter);
//        Thread two = new Thread(counter);
//
//        one.start();
//        two.start();
//        one.join();
//        two.join();
//
//        assertThat(counter.count != 2000000, is(true));
    }
}
