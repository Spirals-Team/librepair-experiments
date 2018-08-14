package edu.boisestate.cs471.model;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

public class SortingAlgorithmResultsTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

//    @Test
    public final void testFoo() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testBubbleSort() {
        System.out.println("Start of test");
        int n = 10;
        BubbleSort sort = new BubbleSort(10);
        sort.randomize();
        for (int i = 0; i < (n*n); i++) {
            boolean isSorted = sort.doSortIteration();
            if (isSorted) {
                return;
            }
        }
        fail("Sorting never completed");
    }

}
