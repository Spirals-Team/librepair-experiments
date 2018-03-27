package ru.job4j.collections.set;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Tests SimpleSet.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 11.10.17;
 * @version $Id$
 * @since 0.1
 */
public class SimpleSetTest {
    /**
     * Хранилище.
     */
    private SimpleSet<String> set;

    /**
     * инициализация.
     */
    @Before
    public void start() {
        set = new SimpleSet<>(10);
        set.add("Ivanov");
        set.add("Ivanov");
        set.add("Petrov");
    }

    /**
     *Тест проверяет увеличение размера масива и уникальность элементов.
     */
    @Test
    public void whenWasAddedTwoElementsEquels() {
        Iterator<String> it = set.iterator();
        it.next();
        String petrov = it.next();
        assertThat(petrov, is("Petrov"));
    }
}