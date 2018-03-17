package ru.job4j.collections.set;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест.
 * @author Hincu Andrei (andreih1981@gmail.com)on 11.10.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class SimpleSetWithLinkedListTest {
    /**
     * Хранилище.
     */
    private SimpleSetWithLinkedList<String> set;



    /**
     * Метод проверяет уникальность элементов работу итератора.
     */
    @Test
    public void whenAddedTwoElementsWithEqualsNames() {
        set = new SimpleSetWithLinkedList<>();
        set.add("Ivanov");
        set.add("Ivanov");
        set.add("Petrov");
        Iterator<String> it = set.iterator();
        String ivanov = it.next();
        assertThat(ivanov, is("Ivanov"));
        String petrov = it.next();
        assertThat(petrov, is("Petrov"));
    }
}