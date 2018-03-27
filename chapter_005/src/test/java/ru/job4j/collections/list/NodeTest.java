package ru.job4j.collections.list;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест.
 * @author Hincu Andrei (andreih1981@gmail.com)on 10.10.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class NodeTest {
    /**
     *Тест на цикличность.
     */
    @Test
    public void hasCycle() {
        Node first = new Node(1);
        Node second = new Node(2);
        Node third = new Node(3);
        Node four = new Node(4);
        first.setNext(second);
        second.setNext(third);
        third.setNext(four);
        four.setNext(second);
        Node.Cycle cycle = new Node.Cycle();
        boolean result = cycle.hasCycle(first);
        assertThat(result, is(true));
    }

    /**
     * Когда один из элементов зациклен сам на себя.
     */
    @Test
    public void whenCycleHasTrue()  {
        Node first = new Node(1);
        Node second = new Node(2);
        Node third = new Node(3);
        Node four = new Node(4);
        first.setNext(second);
        second.setNext(third);
        third.setNext(third);
        four.setNext(second);
        Node.Cycle cycle = new Node.Cycle();
        boolean result = cycle.hasCycle2(first);
        assertThat(result, is(true));
    }
    }
