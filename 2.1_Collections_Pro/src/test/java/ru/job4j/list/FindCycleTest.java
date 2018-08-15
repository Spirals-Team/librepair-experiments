package ru.job4j.list;

import org.junit.Test;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class findCycleTest.
 */
public class FindCycleTest {
    /**
     * Test method hasCycle №1.
     */
    @Test
    public void whenListHasCycleThenReturnTrue() {
        FindCycle fc = new FindCycle();
        Node first = new Node(1);
        Node two = new Node(2);
        Node third = new Node(3);
        Node four = new Node(4);

        first.setNext(two);
        two.setNext(third);
        third.setNext(four);
        four.setNext(first);
        boolean result = fc.hasCycle(first);

        assertThat(result, is(true));
    }

    /**
     * Test method hasCycle №1.
     */
    @Test
    public void whenListHasNotCycleThenReturnFlase() {
        FindCycle fc = new FindCycle();
        Node first = new Node(1);
        Node two = new Node(2);
        Node third = new Node(3);
        Node four = new Node(4);

        first.setNext(two);
        two.setNext(third);
        third.setNext(four);
        boolean result = fc.hasCycle(first);

        assertThat(result, is(false));
    }
}