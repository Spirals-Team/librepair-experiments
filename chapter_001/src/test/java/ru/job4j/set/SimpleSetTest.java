package ru.job4j.set;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Тесты для множества на основе массива с типом Integer
 */
public class SimpleSetTest {

    SimpleSet<Integer> simple = new SimpleSet<>();

    @Before
    public void initValues() {
        simple.add(1);
        simple.add(2);
        simple.add(3);
        simple.add(4);
    }

    @Test
    /**
     * Тест проверяет размер хранилища, затем добавляет объект и проверяет
     * увеличился ли размер массива. Затем пробует добавить в хранилище уже
     * существующий элемент и псле этого проверяет изменение размера хранилища
     */
    public void testAdd() {
        assertThat(simple.size(), is(4));
        simple.add(5);
        assertThat(simple.size(), is(5));
        simple.add(1);
        assertThat(simple.size(), is(5));
    }

    @Test
    /**
     * Тест проверяет работоспособность итератора.
     * Сначало проверяем наличие объекта, потом получаем первый элемент. Проверяем
     * наличие следующего. Переходим в конец множества и проверяем что больше элементов нет.
     */
    public void testIterator() {
        Iterator<Integer> simpleiter = simple.iterator();
        assertThat(simpleiter.hasNext(), is(true));
        assertThat(simpleiter.next(),is(1));
        simpleiter.next();
        assertThat(simpleiter.hasNext(), is(true));
        simpleiter.next();
        simpleiter.next();
        assertThat(simpleiter.hasNext(), is(false));
    }
}