package ru.job4j.collections.generic;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *Тесты .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 05.10.17;
 * @version $Id$
 * @since 0.1
 */
public class SimpleArrayTest {
    /**
     * Тест метода addOrRemove и выход за границы массива.
     * @throws Exception ex.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void whenWasAddedTooMachElements() throws Exception {
    SimpleArray<String> array = new SimpleArray<>(2);
    array.add("Petrov");
    array.add("Ivanov");
    array.add("Sidorov");
    }

    /**
     * Тест методов fdd and getValue.
     */
    @Test
    public void whenAddedTwoElements() {
        SimpleArray<String> array = new SimpleArray<>(2);
        array.add("Petrov");
        array.add("Ivanov");
        assertThat("Ivanov", is(array.getValue(1)));
    }

    /**
     * Тест метода update по индексу обновляемого элемента.
     */
    @Test
    public void whenWasUpdateElementByIndex() {
        SimpleArray<String> array = new SimpleArray<>(2);
        array.add("Petrov");
        array.add("Ivanov");
        array.update(1, "Sidorov");
        assertThat("Sidorov", is(array.getValue(1)));
    }

    /**
     * Тест метода update по значению обновляемого элемента.
     */
    @Test
    public void whenWasUpdateElementByValue() {
        SimpleArray<String> array = new SimpleArray<>(2);
        array.add("Petrov");
        array.add("Ivanov");
        array.update("Ivanov", "Kikabidze");
        assertThat("Kikabidze", is(array.getValue(1)));
    }

    /**
     * Тест метода delete.
     */
    @Test
    public void whenElementWasDeleted() {
        SimpleArray<String> array = new SimpleArray<>(2);
        array.add("Petrov");
        array.add("Ivanov");
        array.delete("Petrov");
        assertThat("Ivanov", is(array.getValue(0)));
        assertThat(null, is(array.getValue(1)));
    }
}