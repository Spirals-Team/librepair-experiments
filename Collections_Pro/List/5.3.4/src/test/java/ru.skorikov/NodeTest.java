package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 29.10.17
 * @ version: java_kurs_standart
 */
public class NodeTest {

    /**
     * Проверим Цикличный контейнер.
     *
     * @throws Exception исключение.
     */
    @Test
    public void isContaynerCycle() throws Exception {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);

        n1.setNext(n2);
        n2.setNext(n3);
        n3.setNext(n1);
        assertThat(Node.hasCycle(n1), is(true));

    }

    /**
     * Проверим Нецикличный контейнер.
     *
     * @throws Exception исключение.
     */
    @Test
    public void isContaynerNoCycle() throws Exception {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);

        n1.setNext(n2);
        n2.setNext(n3);
        n3.setNext(null);

        assertThat(Node.hasCycle(n1), is(false));
    }
    /**
     * Проверим пустой контейнер.
     *
     * @throws Exception исключение.
     */
    @Test
    public void isContaynerNull() throws Exception {
        Node n1 = new Node(1);

        Assert.assertFalse(Node.hasCycle(n1));

    }

}