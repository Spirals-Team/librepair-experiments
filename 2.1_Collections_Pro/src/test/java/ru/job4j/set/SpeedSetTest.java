package ru.job4j.set;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Class SpeedSetTest.
 */
public class SpeedSetTest {
    /**
     * Test speed.
     */
    @Test
    public void testSet() {
        ArraySet<Integer> as = new ArraySet<>();
        long startFirst = System.currentTimeMillis();
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 1111; j++) {
                as.add(j);
            }
        }
        long finishFirst = System.currentTimeMillis();
        System.out.println(finishFirst - startFirst);

        SpeedSet<Integer> ss = new SpeedSet<>();
        long startSecond = System.currentTimeMillis();
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 1111; j++) {
                ss.add(j);
            }
        }
        long finishSecond = System.currentTimeMillis();
        System.out.println(finishSecond - startSecond);

        boolean result = (finishSecond - startSecond) < (finishFirst - startFirst);
        assertThat(result, is(true));
    }
}