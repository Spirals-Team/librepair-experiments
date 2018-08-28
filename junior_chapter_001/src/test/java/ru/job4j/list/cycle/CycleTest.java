package ru.job4j.list.cycle;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CycleTest {
    private Cycle cycle;
    private Node<Integer> one = new Node<>(1);
    private Node<Integer> two = new Node<>(2);
    private Node<Integer> three = new Node<>(3);
    private Node<Integer> four = new Node<>(4);
    private Node<Integer> five = new Node<>(5);

    @Before
    public void setUp() {
        cycle = new Cycle();
    }

    @Test
    public void noLoops() {
        one.setNextNode(two);
        two.setNextNode(three);
        three.setNextNode(four);
        four.setNextNode(five);
        assertThat(cycle.hasCycle(one), is(false));
    }

    @Test
    public void theLoopAtTheBeginning() {
        one.setNextNode(one);
        two.setNextNode(three);
        three.setNextNode(four);
        four.setNextNode(five);
        five.setNextNode(null);
        assertThat(cycle.hasCycle(one), is(true));
    }

    @Test
    public void theLoopAtTheEnd() {
        one.setNextNode(two);
        two.setNextNode(three);
        three.setNextNode(four);
        four.setNextNode(five);
        five.setNextNode(five);
        assertThat(cycle.hasCycle(one), is(true));
    }

    @Test
    public void cycleInTheMiddle() {
        one.setNextNode(two);
        two.setNextNode(three);
        three.setNextNode(three);
        four.setNextNode(five);
        assertThat(cycle.hasCycle(one), is(true));
    }
}