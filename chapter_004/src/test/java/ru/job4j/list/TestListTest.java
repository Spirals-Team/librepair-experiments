package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class TestListTest {
    TestList testList = new TestList();
    TestList.Node<Integer>[] arrayNode = new TestList.Node[5];

    @Before
    public void setUp() {
        arrayNode[0] = new TestList.Node<>(1);
        arrayNode[1] = new TestList.Node<>(2);
        arrayNode[2] = new TestList.Node<>(3);
        arrayNode[3] = new TestList.Node<>(4);
        arrayNode[4] = new TestList.Node<>(5);
    }

    @Test
    public void whenTestNormalQueueNotCircle() {
        arrayNode[0].next = arrayNode[1];
        arrayNode[1].next = arrayNode[2];
        arrayNode[2].next = arrayNode[3];
        arrayNode[3].next = arrayNode[4];
        assertThat(testList.hasCycle(arrayNode[0]), is(false));
    }

    @Test
    public void whenTestQueueCircle() {
        arrayNode[0].next = arrayNode[1];
        arrayNode[1].next = arrayNode[2];
        arrayNode[2].next = arrayNode[3];
        arrayNode[3].next = arrayNode[4];
        arrayNode[4].next = arrayNode[1];
        assertThat(testList.hasCycle(arrayNode[0]), is(true));
    }

    @Test
    public void whenTestQueueCircleInMiddle() {
        arrayNode[0].next = arrayNode[1];
        arrayNode[1].next = arrayNode[2];
        arrayNode[2].next = arrayNode[3];
        arrayNode[3].next = arrayNode[2];
        arrayNode[4].next = arrayNode[1];
        assertThat(testList.hasCycle(arrayNode[0]), is(true));
    }

    @Test
    public void whenTestQueueCircleIn() {
        arrayNode[0].next = arrayNode[1];
        arrayNode[1].next = arrayNode[4];
        arrayNode[2].next = arrayNode[3];
        arrayNode[3].next = arrayNode[2];
        arrayNode[4].next = arrayNode[1];
        assertThat(testList.hasCycle(arrayNode[0]), is(true));
    }

    @Test
    public void whenTestQueueNotCircleIn() {
        arrayNode[0].next = arrayNode[1];
        arrayNode[1].next = arrayNode[2];
        arrayNode[2].next = arrayNode[4];
        arrayNode[3].next = arrayNode[2];
        assertThat(testList.hasCycle(arrayNode[0]), is(false));
    }

    @Test
    public void whenOneNodeInList() {
        TestList.Node<Integer>[] arrayNode = new TestList.Node[1];
        arrayNode[0] = new TestList.Node<>(1);
        assertThat(testList.hasCycle(arrayNode[0]), is(false));
    }
}