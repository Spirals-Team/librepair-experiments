package ru.job4j.list;

import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.hamcrest.Matchers.is;

public class CycleTest {

    @Test
    public void whenTheLatterReffersToFirstThanCycleIsClosedIsTrue() {
        Cycle.Node first = new Cycle.Node<>(1);
        Cycle.Node two = new Cycle.Node<>(2);
        Cycle.Node third = new Cycle.Node<>(3);
        Cycle.Node four = new Cycle.Node<>(4);
        first.next = two;
        two.next = third;
        third.next = four;
        four.next = first;
        assertThat(Cycle.hasCycle(first), is(true));
    }

    @Test
    public void whenTheNodeReffersToPreviousOneThanCycleIsCloseIsTrue() {
        Cycle.Node first = new Cycle.Node<>(1);
        Cycle.Node two = new Cycle.Node<>(2);
        Cycle.Node third = new Cycle.Node<>(3);
        Cycle.Node four = new Cycle.Node<>(4);
        first.next = two;
        two.next = third;
        third.next = two;
        four.next = null;
        assertThat(Cycle.hasCycle(first), is(true));
    }


    @Test
    public void whenTheNodeNotReffersToPreviousOneThanCycleIsCloseIsFalse() {
        Cycle.Node first = new Cycle.Node<>(1);
        Cycle.Node two = new Cycle.Node<>(2);
        Cycle.Node third = new Cycle.Node<>(3);
        Cycle.Node four = new Cycle.Node<>(4);
        first.next = two;
        two.next = third;
        third.next = four;
        four.next = null;
        assertThat(Cycle.hasCycle(first), is(false));
    }
}