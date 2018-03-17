package ru.skorikov;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 01.11.17
 * @ version: java_kurs_standart
 */

public class SimpleSetTest {
    /**
     * For test Exceptions.
     */
    @Rule
    public ExpectedException testException = ExpectedException.none();

    /**
     * Создадим новый контейнер.
     * Добавим 3 одинаковых объекта.
     * В коллекции должен быть один - index равен 1.
     * @throws Exception исключение.
     */
    @Test
    public void thenAddThreeEqualsObjectWhenReturnOne() throws Exception {
        SimpleSet<String> simpleSet = new SimpleSet<>(5);
        simpleSet.add("string");
        simpleSet.add("string");
        simpleSet.add("string");

        assertThat(simpleSet.getIndex(), is(1));
    }
    /**
     * Создадим новый контейнер.
     * Добавим 3 разных объекта.
     * В коллекции должено быть три - index равен 3.
     * @throws Exception исключение.
     */
    @Test
    public void thenAddThreeDifferentObjectWhenReturnThree() throws Exception {
        SimpleSet<String> simpleSet = new SimpleSet<>(5);
        simpleSet.add("string");
        simpleSet.add("string1");
        simpleSet.add("string2");

        assertThat(simpleSet.getIndex(), is(3));
    }

    /**
     * Создадим новый контейнер.
     * Добавим 2 разных объекта.
     * Пробуем получить 3-й
     * @throws Exception исключение.
     */
    @Test
    public void whenAddTwoElementAndGetThirdThenException() throws Exception {
        testException.expect(NoSuchElementException.class);
        SimpleSet<String> simpleSet = new SimpleSet<>(5);
        simpleSet.add("string");
        simpleSet.add("string1");

        simpleSet.next();
        simpleSet.next();
        simpleSet.next();
    }

    /**
     * Создадим новый контейнер на один элемент.
     * Добавим 2 разных объекта.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenAddTwoElementThenConteinerIscrease() throws Exception {
        SimpleSet<String> simpleSet = new SimpleSet<>(1);
        simpleSet.add("string");
        simpleSet.add("string1");

    }
    /**
     * Создадим новый контейнер на один элемент.
     * Добавим объект, и получим новый контейнер.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenAddTOneElementThenReturnNewConteiner() throws Exception {
        SimpleSet<String> simpleSet = new SimpleSet<>(1);
        simpleSet.add("string");
        simpleSet.getSimpleSet();

    }


}