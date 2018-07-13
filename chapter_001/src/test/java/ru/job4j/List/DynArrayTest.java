package ru.job4j.List;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DynArrayTest {

    @Test
    /**
     * Тесты для динамического списка с типом Integer
     */
    public void getAllTestForInteger() {
        DynArray<Integer> simple = new DynArray<>();
        simple.add(1);
        simple.add(2);
        simple.add(3);
        simple.add(4);
        assertThat(simple.size(), is(4));
        simple.add(5);
        assertThat(simple.get(1), is(2));
        Iterator<Integer> simpleiter = simple.iterator();
        assertThat(simpleiter.hasNext(), is(true));
        assertThat(simpleiter.next(),is(1));
        assertThat(simpleiter.hasNext(), is(true));
    }

    @Test(expected = ConcurrentModificationException.class)
    /**
     *Тест ошибки итератора при изменении списка после создания оператора
     */
    public void getIteratorExceptionTest() {
        DynArray<Integer> simple = new DynArray<>();
        simple.add(1);
        simple.add(2);
        Iterator<Integer> simpleiter = simple.iterator();
        simple.add(3);
        simple.add(4);
        simpleiter.hasNext();
    }

}