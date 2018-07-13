package ru.job4j.set;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Тесты для множества на основе связанного списка с типом String
 */
public class SimpleListSetTest {

    SimpleListSet<String> simple = new SimpleListSet<>();

    @Before
    public void initValues() {
        simple.add("first");
        simple.add("second");
        simple.add("third");
        simple.add("four");
    }

    @Test
    /**
     * Тест проверяет размер хранилища, затем добавляет объект и проверяет
     * увеличился ли размер массива. Затем пробует добавить в хранилище уже
     * существующий элемент и псле этого проверяет изменение размера хранилища
     */
    public void testAdd() {
        assertThat(simple.size(), is(4));
        simple.add("five");
        assertThat(simple.size(), is(5));
        simple.add("first");
        assertThat(simple.size(), is(5));
    }

    @Test
    /**
     * Тест проверяет работоспособность итератора.
     * Сначало проверяем наличие объекта, потом получаем первый элемент. Проверяем
     * наличие следующего. Переходим в конец множества и проверяем что больше элементов нет.
     */
    public void testIterator() {
        Iterator<String> simpleiter = simple.iterator();
        assertThat(simpleiter.hasNext(), is(true));
        assertThat(simpleiter.next(),is("first"));
        simpleiter.next();
        assertThat(simpleiter.hasNext(), is(true));
        simpleiter.next();
        simpleiter.next();
        assertThat(simpleiter.hasNext(), is(false));
    }
}