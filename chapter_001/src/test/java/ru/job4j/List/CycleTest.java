package ru.job4j.List;


import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Тест для проверки метода проверки цикличности списка
 */
public class CycleTest {

    Cycle cycle = new Cycle();
    SimpleNode<Integer> first = new SimpleNode(1);
    SimpleNode<Integer> two = new SimpleNode(2);
    SimpleNode<Integer> third = new SimpleNode(3);
    SimpleNode<Integer> four = new SimpleNode(4);
    SimpleNode<Integer> five = new SimpleNode(5);
    SimpleNode<Integer> six = new SimpleNode(6);

    @Test
    /**
     * Тест проверяет что цикличность данной схемы не зависит
     * от того с какого элемента мы начинаем проверять
     */
    public void testSetOne() {
        first.next = two;
        two.next = third;
        third.next = four;
        four.next = five;
        five.next = six;
        six.next = first;
        assertThat(cycle.hasCycle(first), is(true));
        assertThat(cycle.hasCycle(four), is(true));
    }

    @Test
    /**
     * Тест проверяет что в данной схеме есть один цикл и элементы
     * вне цикла
     */
    public void testSetTwo() {
        first.next = two;
        two.next = third;
        third.next = four;
        four.next = first;
        five.next = six;
        six.next = null;
        assertThat(cycle.hasCycle(two), is(true));
        assertThat(cycle.hasCycle(five), is(false));
    }

    @Test
    /**
     * Тест проверяет что в данной схеме есть два цикла
     */
    public void testSetThree() {
        first.next = two;
        two.next = third;
        third.next = first;
        four.next = five;
        five.next = six;
        six.next = four;
        assertThat(cycle.hasCycle(two), is(true));
        assertThat(cycle.hasCycle(five), is(true));
    }
}