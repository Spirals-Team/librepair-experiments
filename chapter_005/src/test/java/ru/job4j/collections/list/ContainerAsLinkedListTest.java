package ru.job4j.collections.list;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *Тесты.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 08.10.17;
 * @version $Id$
 * @since 0.1
 */
public class ContainerAsLinkedListTest {
    /**
     * Хранилище.
     */
    private ContainerAsLinkedList<String> linkedList;

    /**
     * Старт для начала тестов.
     */
    @Before
    public  void start() {
        linkedList = new ContainerAsLinkedList<String>();
        linkedList.add("Petrov");
        linkedList.add("Sidorov");
        linkedList.add("Ivanov");
    }

    /**
     * Тест метода get.
     */
    @Test
    public void whenCalledElementWhisIndexOne() {
        String nameSidorov = linkedList.get(1);
        assertThat(nameSidorov, is("Sidorov"));
    }

    /**
     * Тест итератора.
     */
    @Test
    public void whenUseIterator() {
        Iterator<String> it = linkedList.iterator();
        it.next();
        String name = it.next();
        assertThat(name, is("Sidorov"));
    }

    /**
     * Тест добовления нового  элемента в начало.
     */
    @Test
    public void whenAddedNewFirstElement() {
        linkedList.addFirst("Bond");
        String name = linkedList.get(0);
        assertThat(name, is("Bond"));
    }

    /**
     *Тест метода по удалению последнего элемента.
     */
    @Test
    public void whenWasRemovedLastElement() {
        assertThat(linkedList.getSize(), is(3));
        String last = linkedList.removeLast();
        assertThat(last, is("Ivanov"));
        assertThat(linkedList.getSize(), is(2));
    }
}