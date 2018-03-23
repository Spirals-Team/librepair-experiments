package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class NodeTest {

    private Node first;
    private Node two;
    private Node third;
    private Node four;

    @Before
    public void setUp() {
        first = new Node<>(1);
        two = new Node<>(2);
        third = new Node<>(3);
        four = new Node<>(4);
    }

    @Test
    public void whenLastElementRefersToFirst() {
        first.next = two;
        two.next = third;
        third.next = four;
        four.next = first;

        boolean result = Node.hasCycle(first);
        assertThat(result, is(true));
    }

    @Test
    public void whenListHasCycleInMiddle() {
        first.next = two;
        two.next = third;
        third.next = two;

        boolean result = Node.hasCycle(first);
        assertThat(result, is(true));
    }

    @Test
    public void whenListHasNoCycle() {
        first.next = two;
        two.next = third;
        third.next = four;

        boolean result = Node.hasCycle(first);
        assertThat(result, is(false));
    }
}