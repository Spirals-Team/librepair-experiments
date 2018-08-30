package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class NodeTest {
    Node<Integer> first;
    Node<Integer> second;
    Node<Integer> third;
    Node<Integer> fourth;

    @Before
    public void setUp() {
        first = new Node<>(1);
        second = new Node<>(2);
        third = new Node<>(3);
        fourth = new Node<>(4);
    }

    @Test
    public void whenSingleNodeWithoutCycle() {
        assertThat(first.hasCycle(first), is(false));
    }

    @Test
    public void whenLastNodeLinkToFirstNode() {
        first.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = first;

        assertThat(first.hasCycle(first), is(true));
        assertThat(first.hasCycle(second), is(true));
        assertThat(first.hasCycle(third), is(true));
        assertThat(first.hasCycle(fourth), is(true));
    }

    @Test
    public void whenCycleInTheMiddleOfTheList() {
        first.next = second;
        second.next = third;
        third.next = second;

        assertThat(first.hasCycle(first), is(true));
        assertThat(first.hasCycle(second), is(true));
        assertThat(first.hasCycle(third), is(true));
    }

    @Test
    public void whenNodeCycleItself() {
        first.next = first;
        assertThat(first.hasCycle(first), is(true));
    }

    @Test
    public void whenNodeIsNull() {
        first.next = null;
        assertThat(first.hasCycle(first), is(false));
    }
}