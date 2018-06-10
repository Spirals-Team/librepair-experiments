package ru.job4j.collections;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class NodeTest {

    @Test
    public void whenPollFromQueueThenReturnFirstElement() {
        Node first = new Node(1);
        Node two = new Node(2);
        Node third = new Node(3);
        Node four = new Node(4);
        first.next = two;
        two.next = third;
        third.next = four;
        four.next = first;

        assertThat(first.hasCycle(first), is(true));
        assertThat(first.hasCycle(two), is(true));
        assertThat(first.hasCycle(third), is(true));
        assertThat(first.hasCycle(four), is(true));
    }
}