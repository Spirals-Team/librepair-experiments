package ru.job4j.nonblock;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Class NonBlockCacheTest.
 */
public class NonBlockCacheTest {
    /**
     * Method for testing add.
     */
    @Test
    public void whenAddedOneModelThenSizeByCacheIsOne() {
        NonBlockCache nbc = new NonBlockCache();
        nbc.add(1, "Plane");
        int result = 1;
        assertThat(result, is(nbc.size()));
    }

    /**
     * Method for testing delete.
     */
    @Test
    public void whenAddedTwoModelAndDeletedOneThenSizeByCacheIsOne() {
        NonBlockCache nbc = new NonBlockCache();
        nbc.add(1, "Plane");
        nbc.add(2, "WaterPlane");
        nbc.delete(1);
        int result = 1;
        assertThat(result, is(nbc.size()));
    }

    /**
     * Method for testing update.
     * @throws InterruptedException exception
     */

    @Test
    public void whenTwoThreadsUpdatedOneModelThenThrowException() throws InterruptedException {
        NonBlockCache nbc = new NonBlockCache();
        nbc.add(1, "Plane");


        Thread thrA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    nbc.update(1, "WaterPlane");
                } catch (OptimisticException oe) {
                    System.out.println("Error1");
                }
            }
        });
        Thread thrB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    nbc.update(1, "AirPlane");
                } catch (OptimisticException oe) {
                    System.out.println("Error2");
                }
            }
        });

        thrA.start();
        thrB.start();

        thrA.join();
        thrB.join();
    }
}