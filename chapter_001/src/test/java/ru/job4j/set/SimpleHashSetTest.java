package ru.job4j.set;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleHashSetTest {
    SimpleHashSet<Integer> simple = new SimpleHashSet<>();

    @Before
    public void initValues() {
        simple.add(1);
        simple.add(2);
        simple.add(3);
        simple.add(4);
    }

    @Test
    /**
     * Тест добавляет число в хранилище и потом проверяет размер хранилища
     * затем пытается добавить уже существующее число и проверяет что размер хранилиша не увеличился
     */
    public void testAdd() {
        assertThat(simple.add(5), is(true));
        assertThat(simple.size(), is(5));
        assertThat(simple.add(3), is(false));
        assertThat(simple.size(), is(5));
    }

    @Test
    /**
     * Тест проверяет сначало существующее число, потом отсутствующее
     * затем добавляет отсутствующее число и проверяет его наличие
     */
    public void testContains() {
        assertThat(simple.contains(2), is(true));
        assertThat(simple.contains(5), is(false));
        assertThat(simple.add(5), is(true));
        assertThat(simple.contains(5), is(true));
    }

    @Test
    /**
     * Тест проверяет сначало существующее число, потом удаляет его
     * и проверяет существует ли оно еще в хранилище
     */
    public void testRemove() {
        assertThat(simple.contains(2), is(true));
        assertThat(simple.remove(2), is(true));
        assertThat(simple.contains(2), is(false));
    }
}