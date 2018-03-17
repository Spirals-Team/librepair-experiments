package ru.job4j.collections.list;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/** Test.
 * @author Hincu Andrei (andreih1981@gmail.com) by 06.10.17;
 * @version $Id$
 * @since 0.1
 */
public class SimpleContainerTest {
    /**
     * Тест итератора.
     */
    @Test
    public void whenAddedTwoElementsAndGetElementWithIterator() {
        SimpleContainer<String> strings = new SimpleContainer<>(2);
        strings.add("Ivanov");
        strings.add("Petrov");
        Iterator<String> iterator = strings.iterator();
        iterator.next();
        String name = iterator.next();
        assertThat(name, is("Petrov"));
    }

    /**
     * Тест методов addOrRemove and get с проверкой увеличения длинны массива.
     */
    @Test
    public void whenAddedTwoElementsAndGetSecond() {
        SimpleContainer<String> strings = new SimpleContainer<>(1);
        strings.add("Ivanov");
        strings.add("Petrov");
        String name = strings.get(1);
        assertThat(name, is("Petrov"));
    }
}